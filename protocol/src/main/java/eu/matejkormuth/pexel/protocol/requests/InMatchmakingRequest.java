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
package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Packet that represents matchamking request incoming at master server.
 */
public class InMatchmakingRequest extends Request {
    public String msg = "";
    
    public InMatchmakingRequest() {
    }
    
    public InMatchmakingRequest(final MatchmakingRequest request) {
        this.msg += request.getMinigameName() + "|";
        if (request.getGame() != null) {
            this.msg += request.getGame().getGameUUID().toString() + "|";
        }
        else {
            this.msg += "|";
        }
        String uuids = "";
        for (Player p : request.getPlayers()) {
            uuids += p.getUniqueId().toString();
        }
        // Append, trimming last comma.
        this.msg += uuids.substring(0, uuids.length() - 2);
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(4 + this.msg.length())
                .putInt(this.msg.length())
                .put(this.msg.getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        this.msg = new String(bytes, PexelProtocol.CHARSET);
    }
    
}
