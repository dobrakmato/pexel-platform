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
package eu.matejkormuth.pexel.master.matchmaking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.annotations.JsonType;
import eu.matejkormuth.pexel.commons.arenas.ArenaState;
import eu.matejkormuth.pexel.commons.arenas.LeaveReason;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.ServerInfo;
import eu.matejkormuth.pexel.protocol.requests.OutPlayerMatchmakedMessage;

/**
 * Master side implementation of MatchmakingGame.
 */
@JsonType
public class MatchmakingGameImpl implements MatchmakingGame {
    
    public MatchmakingGameImpl(final ServerInfo slave, final UUID gameId,
            final String minigameName) {
        // This is master only implementation.
        this.host = slave;
        this.gameId = gameId;
        this.minigameName = minigameName;
    }
    
    protected transient ServerInfo host;
    protected UUID                 gameId;
    
    public int                     cached_maximumSlots;
    public ArenaState              cached_state   = ArenaState.WAITING_EMPTY;
    public Set<UUID>               cached_players = new HashSet<UUID>();
    
    // Name of minigame.                                          
    protected String               minigameName;
    
    @Override
    public int getFreeSlots() {
        return this.getMaximumSlots() - this.getPlayerCount();
    }
    
    @Override
    public boolean empty() {
        return this.cached_players.size() == 0;
    }
    
    @Override
    public int getMaximumSlots() {
        return this.cached_maximumSlots;
    }
    
    @Override
    public ArenaState getState() {
        return this.cached_state;
    }
    
    @Override
    public int getPlayerCount() {
        return this.cached_players.size();
    }
    
    @Override
    public boolean canJoin() {
        return this.getFreeSlots() > 0;
    }
    
    @Override
    public boolean canJoin(final int count) {
        return this.getFreeSlots() >= count;
    }
    
    @Override
    public UUID getGameUUID() {
        return this.gameId;
    }
    
    public void connectPlayer(final ProxiedPlayer player) {
        // Send information about player's join on target server.
        this.host.sendRequest(new OutPlayerMatchmakedMessage(player, this.gameId));
        
        this.cached_players.add(player.getUniqueId());
        
        // Cross server teleport.
        PexelMaster.getInstance().getProxy().connect(player, this.host);
    }
    
    @Override
    public List<Player> getPlayers() {
        // TODO Implement MatchmakingGame#getPlayers()
        return null;
    }
    
    public String getMinigameName() {
        return this.minigameName;
    }
    
    @Override
    public boolean contains(final Player player) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void join(final Player player) {
        // Not valid for master server.
        throw new UnsupportedOperationException("join; use connectPlayer() on master");
    }
    
    @Override
    public void leave(final Player player, final LeaveReason reason) {
        // Not valid for master server.
        throw new UnsupportedOperationException("leave");
    }
}
