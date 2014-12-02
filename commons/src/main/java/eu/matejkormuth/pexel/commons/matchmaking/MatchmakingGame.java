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
package eu.matejkormuth.pexel.commons.matchmaking;

import eu.matejkormuth.pexel.commons.PlayerHolder;
import eu.matejkormuth.pexel.commons.arenas.ArenaState;

/**
 * Specifies that the object is game participating in matchmaking.
 */
public interface MatchmakingGame extends PlayerHolder {
    /**
     * Returns number of free slots in the game.
     * 
     * @return free slots count
     */
    public int getFreeSlots();
    
    /**
     * Returns whether this game is empty.
     * 
     * @return true if the game is empty, else otherwise
     */
    public boolean empty();
    
    /**
     * Returns number of all slots in game.
     * 
     * @return all slots count
     */
    public int getMaximumSlots();
    
    /**
     * Returns game actual state.
     * 
     * @return state of arena
     */
    public ArenaState getState();
    
    /**
     * Returns if one player can join game.
     * 
     * @return true or false
     */
    public boolean canJoin();
    
    /**
     * Returns if specified amount of players can join game.
     * 
     * @param count
     *            amount of players
     * @return true or false
     */
    public boolean canJoin(int count);
}
