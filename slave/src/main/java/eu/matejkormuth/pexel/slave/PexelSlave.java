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

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.PluginLoader;
import eu.matejkormuth.pexel.commons.ServerMode;
import eu.matejkormuth.pexel.commons.SlaveServerSoftware;
import eu.matejkormuth.pexel.commons.Storage;
import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.slave.pluginloaders.BukkitPluginLoader;

/**
 * PexelSlave server singleton object.
 */
public class PexelSlave {
    private static PexelSlave instance;
    
    protected SlaveServer     server;
    protected Logger          log;
    protected Configuration   config;
    protected Sync            sync;
    protected PluginLoader    pluginLoader;
    protected Storage         storage;
    
    protected ServerMode      mode;
    
    protected List<Component> components        = new ArrayList<Component>();
    protected boolean         componentsEnabled = false;
    
    public PexelSlave(final File dataFolder, final SlaveServerSoftware software) {
        this.log = new Logger("PexelSlave");
        this.log.timestamp = false;
        
        this.log.info("Booting up PexelSlave...");
        
        // Load configuration.
        File f = new File(dataFolder.getAbsolutePath() + "/config.xml");
        if (!f.exists()) {
            this.log.info("Configuration file not found, generating default one!");
            Configuration.createDefault(ServerType.SLAVE, f);
        }
        this.log.info("Loading configuration...");
        this.config = Configuration.load(f);
        
        // Initialize PluginLoader
        switch (software) {
            case CRAFTBUKKIT:
                this.pluginLoader = new BukkitPluginLoader();
                break;
            case FORGE:
                throw new RuntimeException(
                        "What the hell? You are running unsupported server software!");
            case SPIGOT:
                this.pluginLoader = new BukkitPluginLoader();
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
        
        // Initialize protects.
        
        // Initialize arenas.
        
        // Initialize whole shit from PexelCore.
        
        // Create sync object.
        this.sync = new Sync();
        
        // Connect to master - other thread.
        this.server = new SlaveServer(
                this.config.getAsString(Configuration.KEY_SLAVE_NAME), this.config,
                this.log, new PexelProtocol());
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
    
    public Logger getLogger() {
        return this.log;
    }
    
    public Storage getStorage() {
        return this.storage;
    }
}
