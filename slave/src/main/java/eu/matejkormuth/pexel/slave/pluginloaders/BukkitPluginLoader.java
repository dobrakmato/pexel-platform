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
package eu.matejkormuth.pexel.slave.pluginloaders;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import eu.matejkormuth.pexel.commons.PluginLoader;
import eu.matejkormuth.pexel.commons.storage.MinigameDescriptor;

/**
 * Bukkit implementation of plugin loader.
 */
public class BukkitPluginLoader implements PluginLoader {
    private final PluginManager                   internal;
    private final Map<MinigameDescriptor, Plugin> plugins;
    
    public BukkitPluginLoader() {
        this.internal = Bukkit.getPluginManager();
        this.plugins = new HashMap<MinigameDescriptor, Plugin>();
    }
    
    @Override
    public void load(final MinigameDescriptor plugin) {
        try {
            this.plugins.put(plugin, this.internal.loadPlugin(plugin.getJar()));
        } catch (UnknownDependencyException | InvalidPluginException
                | InvalidDescriptionException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void unload(final MinigameDescriptor plugin) {
        this.internal.disablePlugin(this.plugins.get(plugin));
    }
    
}
