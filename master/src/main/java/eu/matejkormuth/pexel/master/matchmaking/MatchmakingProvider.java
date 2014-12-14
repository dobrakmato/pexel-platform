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
import java.util.UUID;

import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;

/**
 * Class that does matchmaking.
 */
public abstract class MatchmakingProvider {
    protected Matchmaking matchmaking;
    protected Logger      log;
    
    /**
     * Set's instance of {@link Matchmaking} in this {@link MatchmakingProvider}.
     * 
     * @param matchmaking
     *            instance of matchmaking
     */
    protected void setMatchmaking(final Matchmaking matchmaking) {
        this.matchmaking = matchmaking;
        this.log = matchmaking.getLogger();
    }
    
    /**
     * Cancels pending matchmaking request.
     * 
     * @param request
     *            request to cancel
     */
    public abstract void cancelRequest(MatchmakingRequest request);
    
    /**
     * Adds {@link MatchmakingRequest} to implementation queue.
     * 
     * @param request
     *            request to add to queue
     */
    public abstract void addRequest(MatchmakingRequest request);
    
    /**
     * Method that make matches from requests.
     */
    public abstract void doMatchmaking();
    
    /**
     * Returns {@link Collection} of {@link MatchmakingGameImpl} arenas registered in this {@link MatchmakingProvider}.
     * 
     * @return collection of registered arenas
     */
    public abstract Collection<MatchmakingGameImpl> getGames();
    
    /**
     * Registers new minigame to this provider. (Will be used only for statistical purposes.)
     * 
     * @param name
     *            name of minigame
     */
    public abstract void registerMinigame(String minigameName);
    
    /**
     * Registers {@link MatchmakingGameImpl} arena to this {@link MatchmakingProvider}.
     * 
     * @param game
     *            arena to register
     */
    public abstract void registerArena(MatchmakingGameImpl game);
    
    /**
     * Returns game by spcified gameId.
     * 
     * @param gameId
     *            game id
     * @return game object
     */
    public abstract MatchmakingGameImpl getGame(final UUID gameId);
}
