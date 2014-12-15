// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.master;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.LoggerHolder;
import eu.matejkormuth.pexel.commons.ServerComponent;
import eu.matejkormuth.pexel.commons.Storage;
import eu.matejkormuth.pexel.commons.StorageImpl;
import eu.matejkormuth.pexel.master.cache.Caches;
import eu.matejkormuth.pexel.master.db.Database;
import eu.matejkormuth.pexel.master.matchmaking.Matchmaking;
import eu.matejkormuth.pexel.master.matchmaking.MatchmakingProviderImpl;
import eu.matejkormuth.pexel.master.responders.BansResponder;
import eu.matejkormuth.pexel.master.responders.ErrorResponder;
import eu.matejkormuth.pexel.master.responders.MatchmakingResponder;
import eu.matejkormuth.pexel.master.responders.ServerStatusResponder;
import eu.matejkormuth.pexel.master.responders.TeleportationResponder;
import eu.matejkormuth.pexel.master.restapi.ApiServer;
import eu.matejkormuth.pexel.network.Callback;
import eu.matejkormuth.pexel.network.MasterServer;
import eu.matejkormuth.pexel.network.Proxy;
import eu.matejkormuth.pexel.network.ServerInfo;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.protocol.requests.ServerStatusRequest;
import eu.matejkormuth.pexel.protocol.responses.ServerStatusResponse;

/**
 * Pexel master server singleton object.
 */
public final class PexelMaster implements LoggerHolder {
    private static PexelMaster instance = null;
    
    public static final PexelMaster getInstance() {
        return PexelMaster.instance;
    }
    
    private final MasterServer          master;
    private final Logger                log;
    private Configuration               config;
    private final Scheduler             scheduler;
    private final MasterStorageProxy    storage;
    private Database                    database;
    private final Caches                caches;
    
    private final List<MasterComponent> components        = new ArrayList<MasterComponent>();
    private boolean                     componentsEnabled = false;
    
    private PexelMaster(final File dataFolder) {
        this.log = new Logger("PexelMaster");
        this.log.timestamp = true;
        
        try {
            this.log.setOutput(new FileWriter(dataFolder.getAbsolutePath()
                    + "/pexel.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.log.info("Booting up PexelMaster...");
        
        // Load configuration.
        File f = new File(dataFolder.getAbsolutePath() + "/config.xml");
        if (!f.exists()) {
            this.log.info("Configuration file not found!");
            this.config = new Configuration(f);
        }
        else {
            this.log.info("Loading configuration...");
            this.config = Configuration.load(f);
        }
        
        // Load storage.
        File storageFolder = new File(dataFolder.getAbsolutePath() + "/storage");
        storageFolder.mkdirs();
        this.storage = new MasterStorageProxy(new StorageImpl(storageFolder,
                this.config.getSection(StorageImpl.class)));
        this.addComponent(this.storage);
        
        // Set up scheduler.
        this.scheduler = new Scheduler();
        
        // Sheduler basic tasks.
        this.scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                PexelMaster.this.periodic();
            }
        }, 2, TimeUnit.SECONDS);
        
        // Set up caches.
        this.caches = new Caches();
        
        // Set up network.
        this.master = new MasterServer("master",
                this.config.getSection(MasterServer.class), this.log,
                new PexelProtocol());
        
        // Set up responders.
        this.master.getMessenger().addResponder(new ServerStatusResponder());
        this.master.getMessenger().addResponder(new TeleportationResponder());
        this.master.getMessenger().addResponder(new BansResponder());
        this.master.getMessenger().addResponder(new MatchmakingResponder());
        this.master.getMessenger().addResponder(new ErrorResponder());
        
        // Set up Database.
        // this.addComponent(new Database());
        
        // Set up API server.
        this.addComponent(new ApiServer());
        
        // Set up matchmaking.
        this.addComponent(new Matchmaking(new MatchmakingProviderImpl()));
        
        // Set up chat provider.
        this.addComponent(new ChatProvider());
    }
    
    public void start() {
        // Enable components.
        this.log.info("Enabling all components now!");
        this.enableComponents();
    }
    
    /**
     * Shut's down all processes, saves files and turns off server.
     */
    public void shutdown() {
        //TODO: Wait before are all other things done.
        this.log.info("Shutting down server...");
        this.master.shutdown();
        
        this.log.info("Disabling scheduler...");
        this.scheduler.shutdownNow(); // TODO: Too
        
        this.log.info("Disabling all components...");
        this.disableComponents();
        
        this.log.info("Saving configuration...");
        this.config.save();
        
        this.log.info("Shutting down!");
        this.log.info("Thanks for using and bye!");
        
        // Close logger.
        this.log.close();
    }
    
    // Each 2 seconds sends ServerStatusRequest to all servers.
    protected void periodic() {
        this.updateSlaves();
    }
    
    protected void updateSlaves() {
        // Updates slaves.
        for (final SlaveServer slave : this.master.getSlaveServers()) {
            slave.sendRequest(new ServerStatusRequest(
                    new Callback<ServerStatusResponse>() {
                        @Override
                        public void onResponse(final ServerStatusResponse response) {
                            slave.setCustom("maxMem", Long.toString(response.maxMem));
                            slave.setCustom("usedMem", Long.toString(response.usedMem));
                        }
                    }));
        }
    }
    
    /**
     * Adds component to master server.
     * 
     * @param component
     *            component to add
     */
    public void addComponent(final MasterComponent component) {
        this.components.add(component);
        
        if (this.componentsEnabled) {
            component.onEnable();
        }
    }
    
    protected void enableComponents() {
        this.componentsEnabled = true;
        for (MasterComponent c : this.components) {
            this.enableComponent(c);
        }
    }
    
    protected void disableComponents() {
        for (ServerComponent c : this.components) {
            this.disableComponent(c);
        }
    }
    
    protected void enableComponent(final MasterComponent e) {
        this.log.info("Enabling [" + e.getClass().getSimpleName() + "] ...");
        try {
            if (e instanceof MasterComponent) {
                e.master = this;
            }
            e.__initLogger(this);
            e.__initConfig(this.getConfiguration());
            e.onEnable();
        } catch (Exception ex) {
            this.log.info("Error '" + ex.getMessage() + "' while enabling component "
                    + e.getClass().getSimpleName());
        }
    }
    
    protected void disableComponent(final ServerComponent e) {
        this.log.info("Disabling [" + e.getClass().getSimpleName() + "] ...");
        e.onDisable();
    }
    
    public <T extends ServerComponent> T getComponent(final Class<T> type) {
        for (ServerComponent c : this.components) {
            if (type.isInstance(c.getClass())) { return type.cast(c); }
        }
        return null;
    }
    
    public Configuration getConfiguration() {
        return this.config;
    }
    
    public MasterServer getMasterServer() {
        return this.master;
    }
    
    public static void init(final File dataFolder) {
        PexelMaster.instance = new PexelMaster(dataFolder);
    }
    
    @Override
    public Logger getLogger() {
        return this.log;
    }
    
    public Storage getStorage() {
        return this.storage;
    }
    
    public Scheduler getScheduler() {
        return this.scheduler;
    }
    
    public Proxy getProxy() {
        return this.master.getProxy();
    }
    
    public ChatProvider getChatProvider() {
        return this.getComponent(ChatProvider.class);
    }
    
    public Database getDatabase() {
        if (!this.componentsEnabled) { throw new RuntimeException(
                "Database not ready yet! Please use it after onEnable()"); }
        return this.database;
    }
    
    public Caches getCaches() {
        return this.caches;
    }
    
    /**
     * Returns instance of {@link ServerInfo} of server that is used to take care of players that broke something.
     * 
     * @return limbo server info
     */
    public Optional<ServerInfo> getLimboServer() {
        // Connect directly trough proxy.
        Optional<ServerInfo> limbo = this.getProxy().getServer(
                this.getConfiguration()
                        .getSection(PexelMaster.class)
                        .get("limboServerName", "limbo")
                        .asString());
        if (!limbo.isPresent()) {
            this.getLogger().error(
                    "Limbo server not properly configured! Please fix it!");
        }
        return limbo;
    }
}
