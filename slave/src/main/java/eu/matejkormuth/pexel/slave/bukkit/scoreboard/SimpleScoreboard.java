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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Simple one score per player scoreboard.
 * 
 * @author Mato Kormuth
 * 
 */
public class SimpleScoreboard implements ScoreboardView {
    /**
     * Mapping scores.
     */
    private final Map<Player, Score> scores = new HashMap<Player, Score>();
    /**
     * Scoreboard.
     */
    private final Scoreboard         board;
    /**
     * Objective.
     */
    private final Objective          objective;
    
    /**
     * Creates a new simle player-score scoreboard.
     * 
     * @param name
     *            name of objective
     * @param title
     *            title of scoreboard
     */
    public SimpleScoreboard(final String name, final String title) {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.board.registerNewObjective(name, "dummy");
        this.objective.setDisplayName(title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    /**
     * Sets player's score to specified amount.
     * 
     * @param player
     * @param amount
     */
    public void setScore(final Player player, final int amount) {
        if (this.scores.containsKey(player))
            this.scores.get(player).setScore(amount);
        else {
            this.scores.put(player, this.objective.getScore(player.getName()));
            this.scores.get(player).setScore(amount);
        }
    }
    
    /**
     * Increments score for specified player with specified amount.
     * 
     * @param player
     *            player
     * @param amount
     *            amount to incerement
     */
    public void incrementScore(final Player player, final int amount) {
        if (this.scores.containsKey(player))
            this.scores.get(player).setScore(this.scores.get(player).getScore() + amount);
        else {
            this.scores.put(player, this.objective.getScore(player.getName()));
            this.scores.get(player).setScore(amount);
        }
    }
    
    /**
     * Resets scoreboard.
     */
    @Override
    public void reset() {
        for (Score s : this.scores.values())
            s.setScore(0);
        this.scores.clear();
    }
    
    @Override
    public Scoreboard getScoreboard() {
        return this.board;
    }
}
