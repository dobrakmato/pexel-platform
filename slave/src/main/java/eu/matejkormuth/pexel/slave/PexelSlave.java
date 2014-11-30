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
package eu.matejkormuth.pexel.slave;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.matejkormuth.pexel.commons.AbstractObjectFactory;
import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.LoggerHolder;
import eu.matejkormuth.pexel.commons.PluginLoader;
import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.commons.ServerComponent;
import eu.matejkormuth.pexel.commons.ServerMode;
import eu.matejkormuth.pexel.commons.SlaveServerSoftware;
import eu.matejkormuth.pexel.commons.Storage;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.slave.bukkit.BukkitObjectFactory;
import eu.matejkormuth.pexel.slave.pluginloaders.BukkitPluginLoader;

/**
 * PexelSlave server singleton object.
 */
public class PexelSlave implements LoggerHolder {
    private static PexelSlave       instance;
    
    protected SlaveServer           server;
    protected Logger                log;
    protected Configuration         config;
    protected Sync                  sync;
    protected PluginLoader          pluginLoader;
    protected Storage               storage;
    protected AbstractObjectFactory objectFactory;
    
    protected ServerMode            mode;
    
    protected List<ServerComponent> components        = new ArrayList<ServerComponent>();
    protected boolean               componentsEnabled = false;
    
    public PexelSlave(final File dataFolder, final SlaveServerSoftware software) {
        this.log = new Logger("PexelSlave");
        this.log.timestamp = true;
        
        try {
            this.log.setOutput(new FileWriter(dataFolder.getAbsolutePath()
                    + "/pexel.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.log.info("Booting up PexelSlave...");
        
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
        
        // Initialize PluginLoader
        switch (software) {
            case CRAFTBUKKIT:
                this.pluginLoader = new BukkitPluginLoader();
                this.objectFactory = new BukkitObjectFactory();
                break;
            case FORGE:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
            case SPIGOT:
                this.pluginLoader = new BukkitPluginLoader();
                this.objectFactory = new BukkitObjectFactory();
                break;
            case SPONGE:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
            case STATICMC:
                this.pluginLoader = null;
                break;
            default:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
        }
        
        // TODO: Load all plugins.
        
        // Initialize protects.
        
        // Initialize arenas.
        
        // Initialize whole shit from PexelCore.
        
        // Legacy PexelCore <https://github.com/dobrakmato/PexelCore> code.
        this.addComponent(new LegacyCoreComponent());
        
        // Create sync object.
        this.sync = new Sync();
        
        // Connect to master - other thread.
        this.server = new SlaveServer(this.config.getSection(PexelSlave.class)
                .get(Configuration.Keys.KEY_SLAVE_NAME, Providers.RANDOM_NAME.next())
                .asString(), this.config.getSection(SlaveServer.class), this.log,
                new PexelProtocol());
    }
    
    public void shutdown() {
        //TODO: Wait before are all other things done.
        this.log.info("Shutting down server...");
        this.server.shutdown();
        
        this.log.info("Disabling scheduler...");
        //this.scheduler.shutdownNow(); // TODO: Too
        
        this.log.info("Disabling all components...");
        this.disableComponents();
        
        this.log.info("Saving configuration...");
        this.config.save();
        
        this.log.info("Shutting down!");
        this.log.info("Thanks for using and bye!");
        
        // Close logger.
        this.log.close();
    }
    
    /**
     * Adds component to master server.
     * 
     * @param component
     *            component to add
     */
    public void addComponent(final ServerComponent component) {
        this.components.add(component);
        
        if (this.componentsEnabled) {
            component.onEnable();
        }
    }
    
    protected void enableComponents() {
        for (ServerComponent c : this.components) {
            this.enableComponent(c);
        }
    }
    
    protected void disableComponents() {
        for (ServerComponent c : this.components) {
            this.disableComponent(c);
        }
    }
    
    protected void enableComponent(final ServerComponent e) {
        this.log.info("Enabling [" + e.getClass().getSimpleName() + "] ...");
        if (e instanceof SlaveComponent) {
            ((SlaveComponent) e).slave = this;
        }
        e._initLogger(this);
        e._initConfig(this.getConfiguration());
        e.onEnable();
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
    
    public static void init(final File dataFolder, final SlaveServerSoftware software) {
        PexelSlave.instance = new PexelSlave(dataFolder, software);
    }
    
    public static final PexelSlave getInstance() {
        return PexelSlave.instance;
    }
    
    public Configuration getConfiguration() {
        return this.config;
    }
    
    public ServerMode getMode() {
        return this.mode;
    }
    
    public Sync getSync() {
        return this.sync;
    }
    
    @Override
    public Logger getLogger() {
        return this.log;
    }
    
    public Storage getStorage() {
        return this.storage;
    }
    
    public AbstractObjectFactory getObjectFactory() {
        return this.objectFactory;
    }
}
