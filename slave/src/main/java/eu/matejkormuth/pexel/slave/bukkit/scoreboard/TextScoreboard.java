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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class TextScoreboard implements ScoreboardView {
    /**
     * Mapping scores.
     */
    private final Map<String, Score> scores = new HashMap<String, Score>();
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
    public TextScoreboard(final String name, final String title) {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.board.registerNewObjective(name, "dummy");
        this.objective.setDisplayName(title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    /**
     * Sets player's score to specified amount.
     * 
     * @param key
     *            string key
     * @param amount
     *            amount to set
     */
    public void setScore(final String key, final int amount) {
        if (this.scores.containsKey(key))
            this.scores.get(key).setScore(amount);
        else {
            this.scores.put(key, this.objective.getScore(key));
            this.scores.get(key).setScore(amount);
        }
    }
    
    /**
     * Increments score for specified player with specified amount.
     * 
     * @param key
     *            string key
     * @param amount
     *            amount to incerement
     */
    public void incrementScore(final String key, final int amount) {
        if (this.scores.containsKey(key))
            this.scores.get(key).setScore(this.scores.get(key).getScore() + amount);
        else {
            this.scores.put(key, this.objective.getScore(key));
            this.scores.get(key).setScore(amount);
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
    
    /**
     * Clears scoreboard.
     */
    public void clear() {
        this.reset();
    }
}
