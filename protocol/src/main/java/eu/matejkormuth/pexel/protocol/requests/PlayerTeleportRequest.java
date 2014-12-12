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

import eu.matejkormuth.pexel.network.MessageTarget;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Cross server teleportation request.
 */
@MessageTarget(ServerType.MASTER)
public class PlayerTeleportRequest extends Request {
    public String targetServer;
    public UUID   uuid;
    
    public PlayerTeleportRequest() {
    }
    
    public PlayerTeleportRequest(final String targetServer, final UUID uuid) {
        this.targetServer = targetServer;
        this.uuid = uuid;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(this.targetServer.length() + 16)
                .putLong(this.uuid.getMostSignificantBits())
                .putLong(this.uuid.getLeastSignificantBits())
                .put(this.targetServer.getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.uuid = new UUID(buffer.getLong(), buffer.getLong());
        this.targetServer = new String(ByteBuffer.wrap(buffer.array(), 16,
                buffer.array().length - 16)
                .slice()
                .array(), PexelProtocol.CHARSET);
    }
    
}
