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
package eu.matejkormuth.pexel.protocol.responses;

import java.nio.ByteBuffer;

import eu.matejkormuth.pexel.commons.matchmaking.GameState;
import eu.matejkormuth.pexel.network.Response;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

//Sent by slave
public class InMatchmakingStatusResponse extends Response {
    public int       freeSlots;
    public int       maximumSlots;
    public GameState state;
    public int       playerCount;
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(12 + this.state.name().length())
                .putInt(this.freeSlots)
                .putInt(this.maximumSlots)
                .putInt(this.playerCount)
                .put(this.state.name().getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.freeSlots = buffer.getInt();
        this.maximumSlots = buffer.getInt();
        this.playerCount = buffer.getInt();
        this.state = GameState.valueOf(new String(buffer.slice().array(),
                PexelProtocol.CHARSET));
    }
    
}
