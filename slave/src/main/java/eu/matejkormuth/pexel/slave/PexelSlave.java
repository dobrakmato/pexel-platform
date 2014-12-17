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
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.protocol.requests.InServerMetaDataMessage;
import eu.matejkormuth.pexel.slave.bukkit.BukkitObjectFactory;
import eu.matejkormuth.pexel.slave.bukkit.BukkitSlaveMinecraftSoftware;
import eu.matejkormuth.pexel.slave.bukkit.BukkitTeleporter;
import eu.matejkormuth.pexel.slave.components.Matchmaking;
import eu.matejkormuth.pexel.slave.components.TPSChecker;
import eu.matejkormuth.pexel.slave.components.chat.ChatEventHandler;
import eu.matejkormuth.pexel.slave.components.managers.CommandManager;
import eu.matejkormuth.pexel.slave.components.managers.LobbyManager;
import eu.matejkormuth.pexel.slave.events.SlaveEventBus;
import eu.matejkormuth.pexel.slave.events.player.PlayerJoinEvent;
import eu.matejkormuth.pexel.slave.events.player.PlayerLeaveEvent;
import eu.matejkormuth.pexel.slave.pluginloaders.BukkitPluginLoader;
import eu.matejkormuth.pexel.slave.pluginloaders.SlaveComponentLoader;
import eu.matejkormuth.pexel.slave.responders.MatchmakingResponder;
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
    protected StorageImpl           storage;
    protected AbstractObjectFactory objectFactory;
    protected SlaveMinecraftServer  serverSoftware;
    protected Scheduler             scheduler;
    protected SlaveEventBus         eventBus;
    
    protected ServerMode            mode;
    
    protected ComponentList         components        = new ComponentList();
    protected boolean               componentsEnabled = false;
    
    private final List<Player>      onlinePlayers;
    
    private final ClassLoader       classLoader;
    
    public PexelSlave(final File dataFolder, final SlaveMinecraftServerType software,
            final ClassLoader classLoader) {
        this.log = new Logger("PexelSlave");
        this.log.displayTimestamps = true;
        
        this.classLoader = classLoader;
        
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
        
        // To allow PexelSlave static calls from components.
        PexelSlave.instance = this;
        
        // Initialize event bus.
        this.eventBus = new SlaveEventBus();
        
        // Initialize PluginLoader
        switch (software) {
            case CRAFTBUKKIT:
                this.serverSoftware = new BukkitSlaveMinecraftSoftware();
                this.pluginLoader = new BukkitPluginLoader();
                this.objectFactory = new BukkitObjectFactory();
                this.addComponentSystem(new BukkitTeleporter());
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
        
        // Add local matchmaking.
        Matchmaking matchmaking = new Matchmaking();
        this.addComponentSystem(matchmaking);
        
        // Load all components.
        this.log.info("Loading all components...");
        File libsFolder = new File(dataFolder.getAbsoluteFile() + "/libs/");
        libsFolder.mkdirs();
        new SlaveComponentLoader(this.log, this).loadAll(libsFolder);
        
        // TODO: Load all plugins.
        this.pluginLoader.loadAll();
        
        // Initialize protects.
        
        // Initialize arenas.
        
        // Initialize whole shit from PexelCore.
        
        // Register standart event handlers.
        this.eventBus.register(new ChatEventHandler());
        
        // Add standart TPS checker.
        TPSChecker tpsChecker = new TPSChecker();
        this.addComponentSystem(tpsChecker);
        
        // Add managers.
        this.addComponentSystem(new LobbyManager());
        this.addComponentSystem(new CommandManager());
        
        // Create sync object.
        this.sync = new Sync();
        
        // Create scheduler and attach it to sync.
        this.scheduler = new Scheduler();
        this.sync.addTickHandler(this.scheduler);
        // TPS Checker has direct connection to sync.
        this.sync.addTickHandler(tpsChecker);
        
        // Connect to master - other thread.
        this.server = new SlaveServer(this.config.getSection(PexelSlave.class)
                .get(Configuration.Keys.KEY_SLAVE_NAME, Providers.RANDOM_NAME.next())
                .asString(), this.config.getSection(SlaveServer.class), this.log,
                new PexelProtocol());
        
        // Register responders.
        this.server.getMessenger().addResponder(
                new MatchmakingResponder(this.getEventBus()));
        
        // Enable all components before registering on master. Buisness logic (minigames) can be registered trough components.
        this.enableComponents();
        
        // Register myself on master.
        this.server.getMasterServerInfo()
                .sendRequest(
                        new InServerMetaDataMessage(matchmaking.getMinigames(),
                                new HashSet<MapDescriptor>(1),
                                this.serverSoftware.getType(),
                                this.serverSoftware.getVersion(),
                                this.serverSoftware.getSlots()));
        
        // Register all games on master matchmaking.
        matchmaking.registerGamesOnMaster();
    }
    
    public void shutdown() {
        //TODO: Wait before are all other things done.
        this.log.info("Shutting down server...");
        this.server.shutdown();
        
        this.log.info("Disabling scheduler...");
        this.scheduler.shutdownNow();
        
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
    public void addComponentSystem(final SlaveComponent component) {
        this.components.addSystem(component);
        
        if (this.componentsEnabled) {
            component.onEnable();
        }
    }
    
    public void addComponentUser(final SlaveComponent component) {
        this.components.addUser(component);
        
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
        for (SlaveComponent c : this.components) {
            this.disableComponent(c);
        }
    }
    
    protected void enableComponent(final SlaveComponent e) {
        this.log.info("Enabling [" + e.getClass().getSimpleName() + "]...");
        e.slave = this;
        e.__initLogger(this);
        e.__initConfig(this.getConfiguration());
        try {
            e.onEnable();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected void disableComponent(final ServerComponent e) {
        this.log.info("Disabling [" + e.getClass().getSimpleName() + "]...");
        e.onDisable();
    }
    
    public <T extends SlaveComponent> T getComponent(final Class<T> type) {
        for (SlaveComponent c : this.components) {
            if (type.isInstance(c)) { return type.cast(c); }
        }
        return null;
    }
    
    public static void init(final File dataFolder,
            final SlaveMinecraftServerType software) {
        new PexelSlave(dataFolder, software, PexelSlave.class.getClassLoader());
    }
    
    public static void init(final File dataFolder,
            final SlaveMinecraftServerType software, final ClassLoader classLoader) {
        new PexelSlave(dataFolder, software, classLoader);
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
    
    public SlaveServer getServer() {
        return this.server;
    }
    
    public boolean isGameOnlyServer() {
        return this.getConfiguration()
                .getSection(PexelSlave.class)
                .get("gameOnlyServer", false)
                .asBoolean();
    }
    
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }
}
