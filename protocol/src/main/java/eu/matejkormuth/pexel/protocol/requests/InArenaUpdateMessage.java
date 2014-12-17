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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import eu.matejkormuth.pexel.commons.arenas.ArenaState;
import eu.matejkormuth.pexel.network.ByteUtils;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

public class InArenaUpdateMessage extends Request {
    public int        maximumSlots;
    public ArenaState state;
    public Set<UUID>  players;
    public UUID       gameId;
    
    public InArenaUpdateMessage() {
    }
    
    public InArenaUpdateMessage(final UUID gameId, final int maximumSlots,
            final ArenaState state, final Set<UUID> players) {
        this.gameId = gameId;
        this.maximumSlots = maximumSlots;
        this.state = state;
        this.players = players;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        String str = Joiner.on("|").join(this.players);
        return ByteUtils.writeUUID(
                ByteUtils.writeVarString(
                        ByteBuffer.allocate(
                                ByteUtils.INT_SIZE + ByteUtils.UUID_SIZE + 1
                                        + ByteUtils.varStringLength(str))
                                .putInt(this.maximumSlots)
                                .put(this.state.id()), PexelProtocol.CHARSET, str),
                this.gameId);
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.maximumSlots = buffer.getInt();
        this.state = ArenaState.byId(buffer.get());
        String str = ByteUtils.readVarString(buffer, PexelProtocol.CHARSET);
        Iterable<String> uuids = Splitter.on("|").split(str);
        this.players = new HashSet<UUID>();
        for (String uuid : uuids) {
            this.players.add(UUID.fromString(uuid));
        }
        this.gameId = ByteUtils.readUUID(buffer);
    }
}
