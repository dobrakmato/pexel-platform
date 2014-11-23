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
package eu.matejkormuth.pexel.slave.bukkit.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Manager for scoreboard.
 * 
 * @author Mato Kormuth
 * 
 */
public class ScoreboardManager {
    private final ScoreboardView scoreboard;
    
    /**
     * Creates new scoreboard manager, with specified scoreboard.
     * 
     * @param scoreboard
     */
    public ScoreboardManager(final ScoreboardView scoreboard) {
        this.scoreboard = scoreboard;
    }
    
    /**
     * Adds player to this manager.
     * 
     * @param p
     *            player
     */
    public void addPlayer(final Player p) {
        p.setScoreboard(this.scoreboard.getScoreboard());
    }
    
    /**
     * Removes player from this scoreboard manager.
     * 
     * @param p
     *            player
     */
    public void removePlayer(final Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
    
    /**
     * Resets wrapper.
     */
    public void reset() {
        this.scoreboard.reset();
    }
}
