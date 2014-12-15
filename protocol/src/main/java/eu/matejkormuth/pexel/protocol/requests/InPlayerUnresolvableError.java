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

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.network.ByteUtils;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Unresolvable error on player.
 */
public class InPlayerUnresolvableError extends Request {
    public UUID   playerId;
    public String error;
    
    public InPlayerUnresolvableError() {
    }
    
    public InPlayerUnresolvableError(final Player player, final String error) {
        this.playerId = player.getUniqueId();
        this.error = error;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteUtils.writeUUID(
                ByteBuffer.allocate(ByteUtils.UUID_SIZE + ByteUtils.INT_SIZE
                        + this.error.length()), this.playerId)
                .putInt(this.error.length())
                .put(this.error.getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.playerId = ByteUtils.readUUID(buffer);
        int length = buffer.getInt();
        byte[] data = new byte[length];
        buffer.get(data);
        this.error = new String(data, PexelProtocol.CHARSET);
    }
}
