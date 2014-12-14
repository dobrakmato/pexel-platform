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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;

import eu.matejkormuth.pexel.commons.AbstractObjectFactory;
import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.LoggerHolder;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.PluginLoader;
import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.commons.ServerComponent;
import eu.matejkormuth.pexel.commons.ServerMode;
import eu.matejkormuth.pexel.commons.SlaveMinecraftServer;
import eu.matejkormuth.pexel.commons.SlaveMinecraftServerType;
import eu.matejkormuth.pexel.commons.StorageImpl;
import eu.matejkormuth.pexel.commons.storage.MapDescriptor;
import eu.matejkormuth.pexel.commons.storage.MinigameDescriptor;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.protocol.requests.InServerMetaDataMessage;
import eu.matejkormuth.pexel.slave.bukkit.BukkitObjectFactory;
import eu.matejkormuth.pexel.slave.bukkit.BukkitSlaveMinecraftSoftware;
import eu.matejkormuth.pexel.slave.bukkit.BukkitTeleporter;
import eu.matejkormuth.pexel.slave.events.SlaveEventBus;
import eu.matejkormuth.pexel.slave.events.player.PlayerJoinEvent;
import eu.matejkormuth.pexel.slave.events.player.PlayerLeaveEvent;
import eu.matejkormuth.pexel.slave.pluginloaders.BukkitPluginLoader;
import eu.matejkormuth.pexel.slave.pluginloaders.SlaveComponentLoader;
import eu.matejkormuth.pexel.slave.spigot.SpigotSlaveMinecraftServer;
import eu.matejkormuth.pexel.slave.sponge.SpongeObjectFactory;
import eu.matejkormuth.pexel.slave.sponge.SpongePluginLoader;
import eu.matejkormuth.pexel.slave.sponge.SpongeSlaveMinecraftServer;
import eu.matejkormuth.pexel.slave.staticmc.StaticMCSlaveMinecraftSoftware;

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
    protected StorageImpl               storage;
    protected AbstractObjectFactory objectFactory;
    protected SlaveMinecraftServer  serverSoftware;
    protected Scheduler             scheduler;
    protected SlaveEventBus         eventBus;
    
    protected ServerMode            mode;
    
    protected List<SlaveComponent>  components        = new ArrayList<SlaveComponent>();
    protected boolean               componentsEnabled = false;
    
    private final List<Player>      onlinePlayers;
    
    public PexelSlave(final File dataFolder, final SlaveMinecraftServerType software) {
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
                this.serverSoftware = new BukkitSlaveMinecraftSoftware();
                this.pluginLoader = new BukkitPluginLoader();
                this.objectFactory = new BukkitObjectFactory();
                this.addComponent(new BukkitTeleporter());
                break;
            case FORGE:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
            case SPIGOT:
                this.serverSoftware = new SpigotSlaveMinecraftServer();
                this.pluginLoader = new BukkitPluginLoader();
                this.objectFactory = new BukkitObjectFactory();
                break;
            case SPONGE:
                this.serverSoftware = new SpongeSlaveMinecraftServer();
                this.pluginLoader = new SpongePluginLoader();
                this.objectFactory = new SpongeObjectFactory();
                break;
            case STATICMC:
                this.serverSoftware = new StaticMCSlaveMinecraftSoftware();
                this.pluginLoader = null;
                break;
            default:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
        }
        
        // Initialize online players list.
        this.onlinePlayers = new ArrayList<Player>(this.serverSoftware.getSlots());
        
        this.eventBus = new SlaveEventBus();
        
        // Load all components.
        this.log.info("Loading all components...");
        File libsFolder = new File(dataFolder.getAbsoluteFile() + "/libs/");
        libsFolder.mkdirs();
        new SlaveComponentLoader(this.log).loadAll(libsFolder);
        
        // TODO: Load all plugins.
        this.pluginLoader.loadAll();
        
        // Initialize protects.
        
        // Initialize arenas.
        
        // Initialize whole shit from PexelCore.
        
        // Legacy PexelCore <https://github.com/dobrakmato/PexelCore> code.
        //this.addComponent(new LegacyCoreComponent());
        
        // Create sync object.
        this.sync = new Sync();
        
        // Create scheduler and attach it to sync.
        this.scheduler = new Scheduler();
        this.sync.addTickHandler(this.scheduler);
        
        // Connect to master - other thread.
        this.server = new SlaveServer(this.config.getSection(PexelSlave.class)
                .get(Configuration.Keys.KEY_SLAVE_NAME, Providers.RANDOM_NAME.next())
                .asString(), this.config.getSection(SlaveServer.class), this.log,
                new PexelProtocol());
        
        this.server.getMasterServerInfo()
                .sendRequest(
                        new InServerMetaDataMessage(new HashSet<MinigameDescriptor>(1),
                                new HashSet<MapDescriptor>(1),
                                this.serverSoftware.getType(),
                                this.serverSoftware.getVersion(),
                                this.serverSoftware.getSlots()));
        
        this.enableComponents();
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
    
    public void addPlayer(final Player player) {
        this.onlinePlayers.add(player);
        // Fire player join events.
        this.eventBus.post(new PlayerJoinEvent(player));
    }
    
    public void removePlayer(final Player player) {
        this.onlinePlayers.remove(player);
        // Fire player left events.
        this.eventBus.post(new PlayerLeaveEvent(player));
    }
    
    public Optional<Player> getPlayer(final UUID uuid) {
        for (Player p : this.onlinePlayers) {
            if (p.getUniqueId().equals(uuid)) { return Optional.of(p); }
        }
        return Optional.absent();
    }
    
    public Optional<Player> getPlayer(final String name) {
        for (Player p : this.onlinePlayers) {
            if (p.getName().equals(name)) { return Optional.of(p); }
        }
        return Optional.absent();
    }
    
    /**
     * Adds component to master server.
     * 
     * @param component
     *            component to add
     */
    public void addComponent(final SlaveComponent component) {
        this.components.add(component);
        
        if (this.componentsEnabled) {
            component.onEnable();
        }
    }
    
    protected void enableComponents() {
        for (SlaveComponent c : this.components) {
            this.enableComponent(c);
        }
    }
    
    protected void disableComponents() {
        for (ServerComponent c : this.components) {
            this.disableComponent(c);
        }
    }
    
    protected void enableComponent(final SlaveComponent e) {
        this.log.info("Enabling [" + e.getClass().getSimpleName() + "] ...");
        if (e instanceof SlaveComponent) {
            e.slave = this;
        }
        e.__initLogger(this);
        e.__initConfig(this.getConfiguration());
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
    
    public static void init(final File dataFolder,
            final SlaveMinecraftServerType software) {
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
    
    public StorageImpl getStorage() {
        return this.storage;
    }
    
    public SlaveEventBus getEventBus() {
        return this.eventBus;
    }
    
    public Scheduler getScheduler() {
        return this.scheduler;
    }
    
    public AbstractObjectFactory getObjectFactory() {
        return this.objectFactory;
    }
    
    /**
     * Returns list of online players.
     * 
     * @return list of online players
     */
    public List<Player> getOnlinePlayers() {
        return this.onlinePlayers;
    }
}
