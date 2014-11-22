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

import java.util.List;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.matchmaking.GameState;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.MessageExtender;
import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.ServerInfo;
import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.protocol.requests.OutMatchmakingGameStatusRequest;
import eu.matejkormuth.pexel.protocol.responses.InMatchmakingStatusResponse;

/**
 * Master side implementation of MatchmakingGame.
 */
public class MatchmakingGameImpl extends
        MessageExtender<OutMatchmakingGameStatusRequest, InMatchmakingStatusResponse>
        implements MatchmakingGame {
    
    public MatchmakingGameImpl(final ServerInfo slave, final UUID gameId) {
        // This is master only implementation.
        super(ServerType.MASTER);
        this.host = slave;
        this.gameId = gameId;
    }
    
    protected ServerInfo host;
    protected UUID       gameId;
    
    protected int        cached_freeSlots;
    protected int        cached_maximumSlots;
    protected GameState  cached_state;
    protected int        cached_playerCount; // Could be replaced with player list.
                                              
    @Override
    public int getFreeSlots() {
        return this.cached_freeSlots;
    }
    
    @Override
    public boolean empty() {
        return this.cached_playerCount == 0;
    }
    
    @Override
    public int getMaximumSlots() {
        return this.cached_maximumSlots;
    }
    
    @Override
    public GameState getState() {
        return this.cached_state;
    }
    
    @Override
    public int getPlayerCount() {
        return this.cached_playerCount;
    }
    
    @Override
    public boolean canJoin() {
        return this.getFreeSlots() > 0;
    }
    
    @Override
    public boolean canJoin(final int count) {
        return this.getFreeSlots() >= count;
    }
    
    public void connectPlayer(final ProxiedPlayer player) {
        // Start onPlayerJoin on slave server.
        
        // Cross server teleport.
        PexelMaster.getInstance().getProxy().connect(player, this.host);
    }
    
    @Override
    public void onResponse(final InMatchmakingStatusResponse response) {
        this.cached_freeSlots = response.freeSlots;
        this.cached_maximumSlots = response.maximumSlots;
        this.cached_playerCount = response.playerCount;
        this.cached_state = response.state;
    }
    
    @Override
    public OutMatchmakingGameStatusRequest onRequest() {
        return new OutMatchmakingGameStatusRequest(this.getCallback(), this.gameId);
    }
    
    @Override
    public List<ProxiedPlayer> getPlayers() {
        // TODO Implement MatchmakingGame#getPlayers()
        return null;
    }
}
