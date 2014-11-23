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
package eu.matejkormuth.pexel.slave.bukkit.actions;

import org.bukkit.Location;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * Inventory action that teleports player to specified location.
 */
public class TeleportAction implements Action {
    /**
     * Target location.
     */
    private final Location location;
    
    /**
     * Target server.
     */
    private ServerInfo     server;
    
    /**
     * Creates a new action.
     * 
     * @param location
     */
    public TeleportAction(final Location location) {
        this.location = location;
    }
    
    /**
     * Creates a new action.
     * 
     * @param location
     */
    public TeleportAction(final Location location, final ServerInfo server) {
        this.location = location;
        this.server = server;
    }
    
    @Override
    public void execute(final Player player) {
        if (this.server.isLocal()) {
            // Just teleport player to target location.
            player.teleport(this.location);
        }
        else {
            // TODO: Add teleport to location. Perform server-wide teleport
            
            // Teleport to other server when using Bungee.
            // ByteArrayDataOutput out = ByteStreams.newDataOutput();
            // out.writeUTF("Connect");
            // out.writeUTF(this.server.());
            // player.sendPluginMessage(Pexel.getCore(), "BungeeCord", out.toByteArray());
        }
    }
}
