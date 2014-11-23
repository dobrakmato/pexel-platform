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
package eu.matejkormuth.pexel.slave.bukkit.commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * Command manager class that supports bukkit commands API.
 */
public class BukkitCommandManager extends CommandManager implements Listener {
    /**
     * Initializes new instance of {@link CommandManager} that supports bukkit.
     */
    public BukkitCommandManager() {
        Bukkit.getPluginManager().registerEvents(this, Pexel.getCore());
    }
    
    @EventHandler
    public void onCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/")) {
            boolean success = this.parseCommand(event.getPlayer(),
                    event.getMessage().substring(1));
            if (success)
                event.setCancelled(true);
        }
        else {
            boolean success = this.parseCommand(event.getPlayer(), event.getMessage());
            if (success)
                event.setCancelled(true);
        }
    }
}
