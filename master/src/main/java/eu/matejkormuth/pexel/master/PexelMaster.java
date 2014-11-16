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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import eu.matejkormuth.pexel.network.Callback;
import eu.matejkormuth.pexel.network.MasterServer;
import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.network.requests.ServerStatusRequest;
import eu.matejkormuth.pexel.network.responses.ServerStatusResponse;
import eu.matejkormuth.pexel.utils.Configuration;
import eu.matejkormuth.pexel.utils.Logger;

/**
 * Pexel master server singleton object.
 */
public final class PexelMaster {
    private static PexelMaster instance = null;
    
    public static final PexelMaster getInstance() {
        return PexelMaster.instance;
    }
    
    protected MasterServer    master;
    protected Logger          log;
    protected Configuration   config;
    protected final Scheduler scheduler;
    
    protected List<Component> components        = new ArrayList<Component>();
    protected boolean         componentsEnabled = false;
    
    private PexelMaster(final File dataFolder) {
        this.log = new Logger("PexelMaster");
        this.log.timestamp = false;
        
        this.log.info("Booting up PexelMaster...");
        
        // Load configuration.
        File f = new File(dataFolder.getAbsolutePath() + "/config.xml");
        if (!f.exists()) {
            this.log.info("Configuration file not found, generating default one!");
            Configuration.createDefault(ServerType.MASTER, f);
        }
        this.log.info("Loading configuration...");
        this.config = Configuration.load(f);
        
        // Set up scheduler.
        this.scheduler = new Scheduler();
        
        // Sheduler basic tasks.
        this.scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                PexelMaster.this.periodic();
            }
        }, 2, TimeUnit.SECONDS);
        
        // Set up network.
        this.master = new MasterServer("master", this.config, this.log);
        
        // Set up API server.
        
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
    public void addComponent(final Component component) {
        this.components.add(component);
        
        if (this.componentsEnabled) {
            component.onEnable();
        }
    }
    
    protected void enableComponents() {
        for (Component c : this.components) {
            this.enableComponent(c);
        }
    }
    
    protected void disableComponents() {
        for (Component c : this.components) {
            this.disableComponent(c);
        }
    }
    
    protected void enableComponent(final Component e) {
        this.log.info("Enabling " + e.getClass().getSimpleName() + "...");
        e.onEnable();
    }
    
    protected void disableComponent(final Component e) {
        this.log.info("Disabling " + e.getClass().getSimpleName() + "...");
        e.onDisable();
    }
    
    public Configuration getConfiguration() {
        return this.config;
    }
    
    public MasterServer getServer() {
        return this.master;
    }
    
    public static void init(final File dataFolder) {
        PexelMaster.instance = new PexelMaster(dataFolder);
    }
    
    public Logger getLogger() {
        return this.log;
    }
}
