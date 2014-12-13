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
package eu.matejkormuth.pexel.slave.bukkit;

import org.bukkit.Bukkit;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.TeleportProvider;
import eu.matejkormuth.pexel.slave.SlaveComponent;

/**
 * Bukkit {@link TeleportProvider} implemenation.
 */
public class BukkitTeleporter extends SlaveComponent implements TeleportProvider {
    
    @Override
    public void teleport(final Player player, final Location location) {
        org.bukkit.Location loc = new org.bukkit.Location(
                Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch());
        Bukkit.getPlayer(player.getUUID()).teleport(loc);
    }
    
}
