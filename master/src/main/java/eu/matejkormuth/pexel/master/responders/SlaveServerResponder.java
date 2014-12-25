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
import eu.matejkormuth.pexel.master.matchmaking.Matchmaking;
import eu.matejkormuth.pexel.network.ServerInfo;
import eu.matejkormuth.pexel.network.SlaveServer;
import eu.matejkormuth.pexel.protocol.requests.InServerMetaDataMessage;
import eu.matejkormuth.pexel.protocol.requests.InUnregisterSlaveRequest;

/**
 * Responder for server status packets.
 */
public class SlaveServerResponder {
    public void onInServerMetaDataMessage(final InServerMetaDataMessage msg) {
        msg.getSender().setCustom("softwareVersion", msg.softwareVersion);
        msg.getSender().setCustom("software", msg.software.toString());
        msg.getSender().setCustom("slots", msg.slots);
        msg.getSender().setCustom("maps", msg.maps);
        msg.getSender().setCustom("minigames", msg.minigames);
    }
    
    public void onSlaveUnresgister(final InUnregisterSlaveRequest msg) {
        ServerInfo slave = msg.getSender();
        // Unregister registered games from matchmaking.
        PexelMaster.getInstance()
                .getComponent(Matchmaking.class)
                .unregisterSlaveGames(slave);
        // Remove from slaves on master.
        PexelMaster.getInstance()
                .getMasterServer()
                .removeSlave((SlaveServer) msg.getSender());
    }
}
