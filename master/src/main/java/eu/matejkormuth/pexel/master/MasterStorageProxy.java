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

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Storage;
import eu.matejkormuth.pexel.commons.StorageImpl;
import eu.matejkormuth.pexel.commons.storage.MapDescriptor;
import eu.matejkormuth.pexel.commons.storage.MinigameDescriptor;

/**
 * Proxy object to allow usage of {@link StorageImpl} as {@link MasterComponent} by using {@link Storage} methods.
 */
public class MasterStorageProxy extends MasterComponent implements Storage {
    private final StorageImpl storage;
    
    public MasterStorageProxy(final StorageImpl target) {
        this.storage = target;
    }
    
    @Override
    public void onEnable() {
        this.storage.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.storage.onDisable();
    }
    
    @Override
    public Collection<MinigameDescriptor> getAvaiablePlugins() {
        return this.storage.getAvaiablePlugins();
    }
    
    @Override
    public Collection<MapDescriptor> getAvaiableMaps() {
        return this.storage.getAvaiableMaps();
    }
    
    @Override
    public boolean hasPlugin(final String pluginName) {
        return this.storage.hasPlugin(pluginName);
    }
}
