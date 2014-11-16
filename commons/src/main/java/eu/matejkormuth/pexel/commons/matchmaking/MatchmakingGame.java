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

import java.util.List;

import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.commons.arenas.DisconnectReason;

/**
 * Specifies that the object is game participating in matchmaking.
 * 
 * @author Mato Kormuth
 * 
 */
public interface MatchmakingGame {
    /**
     * Returns number of free slots in the game.
     * 
     * @return free slots count
     */
    public int getFreeSlots();
    
    /**
     * Returns number of all slots in game.
     * 
     * @return all slots count.
     */
    public int getMaximumSlots();
    
    /**
     * Returns game actual state.
     * 
     * @return state of arena
     */
    public GameState getState();
    
    /**
     * Returns list of players in game.
     * 
     * @return
     */
    public List<Player> getPlayers();
    
    /**
     * Returns number of player in game.
     * 
     * @return number of players in arena
     */
    public int getPlayerCount();
    
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
    
    /**
     * Called when player joins the game.
     * 
     * @param player
     *            player who joined arena
     */
    public void onPlayerJoin(Player player);
    
    /**
     * Called when player lefts the game.
     * 
     * @param player
     *            player who left arena
     */
    public void onPlayerLeft(Player player, DisconnectReason reason);
}
