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
package eu.matejkormuth.pexel.slave;

import java.util.List;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.matchmaking.GameState;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.network.ProxiedPlayer;

/**
 * Slave side implementation of {@link MatchmakingGame}.
 */
public class MatchmakingGameImpl implements MatchmakingGame {
    protected UUID gameId;
    
    public MatchmakingGameImpl() {
        this.gameId = UUID.randomUUID();
    }
    
    @Override
    public int getFreeSlots() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public boolean empty() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public int getMaximumSlots() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public GameState getState() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<ProxiedPlayer> getPlayers() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getPlayerCount() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public boolean canJoin() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean canJoin(final int count) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
