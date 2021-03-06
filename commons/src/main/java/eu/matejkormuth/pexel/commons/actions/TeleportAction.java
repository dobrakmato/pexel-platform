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
package eu.matejkormuth.pexel.commons.actions;

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * {@link Action} that teleports player to specified location and/or server.
 */
public class TeleportAction implements Action {
    private final Location   location;
    private final ServerInfo server;
    
    /**
     * Constructs new {@link TeleportAction} that teleports player to specified {@link Location} at specified server.
     * Use {@link ServerInfo#localServer()} to obtain local {@link ServerInfo}.
     * 
     * @param server
     *            server to teleport player to
     * @param location
     *            target location on server
     */
    public TeleportAction(final ServerInfo server, final Location location) {
        this.server = server;
        this.location = location;
    }
    
    @Override
    public void execute(final Player player) {
        // If is player at target server.
        if (this.server == ServerInfo.localServer()) {
            // Just teleport him to location.
            player.teleport(this.location);
        }
        else {
            // TODO: Perform cross server teleport.
        }
    }
    
    @Override
    public void execute(final Collection<Player> players) {
        // If is player at target server.
        if (this.server == ServerInfo.localServer()) {
            for (Player player : players) {
                // Just teleport him to location.
                player.teleport(this.location);
            }
        }
        else {
            // TODO: Perform cross server teleport.
        }
    }
    
    public ServerInfo getServer() {
        return this.server;
    }
    
    public Location getLocation() {
        return this.location;
    }
}
