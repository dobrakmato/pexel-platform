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
import java.util.UUID;

import eu.matejkormuth.pexel.network.ByteUtils;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Request for registering in matchmaking.
 */
public class InMatchmakingRegisterGameMessage extends Request {
    public UUID   gameId;
    public int    maxPlayers;
    public String minigame;
    
    public InMatchmakingRegisterGameMessage() {
    }
    
    public InMatchmakingRegisterGameMessage(final UUID gameId, final String minigame,
            final int maxPlayers) {
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.minigame = minigame;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteUtils.writeVarString(
                ByteUtils.writeUUID(
                        ByteBuffer.allocate(ByteUtils.UUID_SIZE + ByteUtils.INT_SIZE
                                + ByteUtils.varStringLength(this.minigame)), this.gameId),
                PexelProtocol.CHARSET, this.minigame)
                .putInt(this.maxPlayers);
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.gameId = ByteUtils.readUUID(buffer);
        this.minigame = ByteUtils.readVarString(buffer, PexelProtocol.CHARSET);
        this.maxPlayers = buffer.getInt();
    }
    
}
