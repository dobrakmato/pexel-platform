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

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;

/**
 * Class that does matchmaking.
 */
public abstract class MatchmakingProvider {
    protected Matchmaking matchmaking;
    protected Logger      log;
    
    protected void setMatchmaking(final Matchmaking matchmaking) {
        this.matchmaking = matchmaking;
        this.log = matchmaking.getLogger();
    }
    
    abstract void cancelRequest(MatchmakingRequest request);
    
    abstract void addRequest(MatchmakingRequest request);
    
    abstract void doMatchmaking();
    
    abstract Collection<MatchmakingGameImpl> getGames();
}
