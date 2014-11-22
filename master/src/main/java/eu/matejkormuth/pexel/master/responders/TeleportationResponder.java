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
package eu.matejkormuth.pexel.master.responders;

import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.Proxy;
import eu.matejkormuth.pexel.protocol.requests.PlayerTeleportRequest;

/**
 * Responder for teleportation between servers.
 */
public class TeleportationResponder {
    // Proxy server instance;
    Proxy proxy;
    
    public void onPlayerTeleport(final PlayerTeleportRequest request) {
        if (this.proxy == null) {
            this.proxy = PexelMaster.getInstance().getProxy();
        }
        
        // Get player.
        ProxiedPlayer player = this.proxy.getPlayer(request.uuid);
        // Redirect him.
        this.proxy.connect(
                player,
                PexelMaster.getInstance()
                        .getMasterServer()
                        .getSlave(request.targetServer));
    }
}
