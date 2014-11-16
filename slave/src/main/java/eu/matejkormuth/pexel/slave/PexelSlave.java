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

import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.utils.Configuration;
import eu.matejkormuth.pexel.utils.Logger;

/**
 * PexelSlave server singleton object.
 */
public class PexelSlave {
    private static PexelSlave instance;
    
    protected SlaveServer     server;
    protected Logger          log;
    protected Configuration   config;
    protected Sync            sync;
    
    protected List<Component> components        = new ArrayList<Component>();
    protected boolean         componentsEnabled = false;
    
    public PexelSlave(final File dataFolder) {
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
        
        // Initialize protects.
        
        // Initialize arenas.
        
        // Initialize whole shit from PexelCore.
        
        // Create sync object.
        this.sync = new Sync();
        
        // Connect to master - other thread.
        this.server = new SlaveServer(
                this.config.getAsString(Configuration.KEY_SLAVE_NAME), this.config,
                this.log);
    }
    
    public static void init(final File dataFolder) {
        PexelSlave.instance = new PexelSlave(dataFolder);
    }
    
    public static final PexelSlave getInstance() {
        return PexelSlave.instance;
    }
    
    public Configuration getConfiguration() {
        return this.config;
    }
    
    public Sync getSync() {
        return this.sync;
    }
    
    public Logger getLogger() {
        return this.log;
    }
}
