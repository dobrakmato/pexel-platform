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
package eu.matejkormuth.pexel.slave.bukkit.stats;

/**
 * Java interface for mertex statistics. (stats.mertex.eu)
 * 
 * @author Mato Kormuth
 * 
 * @see StatsClient#setStat(String, String, int)
 * @see StatsClient#setAchievement(String, String)
 * @see StatsClient#incrementStat(String, String)
 */
public abstract class StatsClient {
    //Dokonci.
    /**
     * Adds flag for specified player for specified achievement.
     * 
     * @param pid
     *            player id
     * @param achievement
     *            achievement id
     */
    public void setAchievement(final String pid, final String achievement) {
        this.setStat(pid, "achievement." + achievement, 1);
    }
    
    /**
     * Sets stat of player to zero.
     * 
     * @param pid
     *            player id
     * @param stat
     *            statistic id
     */
    public void resetStat(final String pid, final String stat) {
        this.setStat(pid, stat, 0);
    }
    
    /**
     * Increments player's stat by one.
     * 
     * @param pid
     *            player id
     * @param stat
     *            statistic id
     */
    public void incrementStat(final String pid, final String stat) {
        this.incrementStat(pid, stat, 1);
    }
    
    /**
     * Decrements player's stat by one.
     * 
     * @param pid
     *            player id
     * @param stat
     *            statistic id
     */
    public void decrementStat(final String pid, final String stat) {
        this.decrementStat(pid, stat, 1);
    }
    
    /**
     * Sets player's stat to specified value.
     * 
     * @param pid
     *            player id
     * @param stat
     *            static id
     * @param value
     *            value of stat to be set
     */
    public abstract void setStat(final String pid, final String stat, final int value);
    
    /**
     * Increments player's stat by one.
     * 
     * @param pid
     *            player id
     * @param stat
     *            statistic id
     * @param amount
     *            amount to increment
     */
    public abstract void incrementStat(final String pid, final String stat,
            final int amount);
    
    /**
     * Decrements player's stat by one.
     * 
     * @param pid
     *            player id
     * @param stat
     *            statistic id
     * @param amount
     *            amount to decrement
     */
    public abstract void decrementStat(final String pid, final String stat,
            final int amount);
    
    /**
     * Sets all stats of specified player to zero.
     * 
     * @param pid
     *            player id
     */
    public abstract void resetStats(final String pid);
    
    /**
     * Registers new user or loads his info.
     * 
     * @param pid
     *            player id
     * @param name
     *            player name
     * @param email
     *            player's email
     */
    public abstract void registerUser(final String pid, final String name,
            final String email);
}
