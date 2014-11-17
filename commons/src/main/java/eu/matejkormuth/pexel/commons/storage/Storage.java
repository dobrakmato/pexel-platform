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
package eu.matejkormuth.pexel.commons.storage;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.base.Preconditions;

import eu.matejkormuth.pexel.commons.Component;
import eu.matejkormuth.pexel.network.ServerSide;

/**
 * Class that represents storage.
 */
public class Storage extends Component {
    protected ServerSide              side;
    protected File                    rootFolder;
    
    protected Set<MinigameDescriptor> minigames = new HashSet<MinigameDescriptor>();
    protected Set<MapDescriptor>      maps      = new HashSet<MapDescriptor>();
    protected Set<String>             tags      = new HashSet<String>();
    
    private final ReentrantLock       lock      = new ReentrantLock();
    
    /**
     * Creates new Storage wrapper object on spcified folder.
     * 
     * @param storageFolder
     */
    public Storage(final File storageFolder) {
        Preconditions.checkNotNull(storageFolder);
        Preconditions.checkArgument(storageFolder.exists(), "storageFolder must exist");
        Preconditions.checkArgument(storageFolder.isDirectory(),
                "storageFolder must be directory");
        
        this.rootFolder = storageFolder;
    }
    
    @Override
    public void onEnable() {
        this.expandStructure();
        this.loadIndex();
        this.updateIndex();
    }
    
    // Method that returns File object based from relative path.
    private File getFile(final String relative) {
        return new File(this.rootFolder.getAbsolutePath() + "/" + relative);
    }
    
    // Method that expands folder structure.
    private void expandStructure() {
        boolean expanded = false;
        expanded |= this.getFile("maps").mkdir();
        expanded |= this.getFile("minigames").mkdir();
        
        if (expanded) {
            this.logger.info("Directory structure expanded!");
        }
    }
    
    private void loadIndex() {
        File index = this.getFile("index.xml");
        if (index.exists()) {
            //this.readIndex();
        }
        else {
            this.logger.info("Index not found!");
        }
    }
    
    private void updateIndex() {
        this.logger.info("Updating index file...");
        this.scanAsync();
    }
    
    // Called from another thread.
    private void scanAsync() {
        this.logger.info("Starting async file structure scan...");
        this.lock.lock();
        
        // Maps
        File maps = this.getFile("maps");
        for (String folder : maps.list()) {
            if (new File(maps, folder).isDirectory()) {
                this.scanMapDir(new File(maps, folder));
            }
        }
        
        // Plugins
        File plugins = this.getFile("minigames");
        for (String folder : plugins.list()) {
            if (new File(plugins, folder).isDirectory()) {
                this.scanPluginDir(new File(plugins, folder));
            }
        }
        
        this.lock.unlock();
        this.logger.info("File structure scan finished!");
        this.logger.info("Found " + this.minigames.size() + " plugins, "
                + this.maps.size() + " maps, " + this.tags.size() + " tags!");
    }
    
    private void scanPluginDir(final File folder) {
        File jar = new File(folder, folder.getName() + ".jar");
        File desc = new File(folder, "description.xml");
        
        if (jar.exists() && desc.exists()) {
            MinigameDescriptor descriptor = MinigameDescriptor.load(desc);
            // Add minigame plugin.
            this.minigames.add(descriptor);
            
            // Add tags.
            this.tags.addAll(Arrays.asList(descriptor.getTags()));
        }
        else {
            this.logger.error("Folder " + folder.getAbsolutePath()
                    + " is not valid plugin package!");
        }
    }
    
    private void scanMapDir(final File folder) {
        
    }
    
    public Collection<MinigameDescriptor> getAvaiablePlugins() {
        return Collections.unmodifiableSet(this.minigames);
    }
    
    public Collection<MapDescriptor> getAvaiableMaps() {
        return Collections.unmodifiableSet(this.maps);
    }
}
