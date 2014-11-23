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
package eu.matejkormuth.pexel.slave.bukkit.util;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * Class used for countdown.
 * 
 * @author Mato Kormuth
 * 
 */
public class Countdown {
    private int      timeLeft   = 0;
    private int      timeLenght = 0;
    private int      taskId     = 0;
    private Runnable onEnd;
    private Runnable onTick;
    private String   tag        = null;
    
    /**
     * Creates new countdown with specified time left.
     * 
     * @param seconds
     *            time lieft in seconds
     */
    public Countdown(final int seconds) {
        this.timeLenght = seconds;
        this.timeLeft = seconds;
    }
    
    /**
     * Creates new countdown with specified time left.
     * 
     * @param seconds
     *            time lieft in seconds
     * @param tag
     *            tag of countdown
     */
    public Countdown(final int seconds, final String tag) {
        this.timeLenght = seconds;
        this.timeLeft = seconds;
        this.tag = tag;
    }
    
    /**
     * Starts the countdown.
     */
    public void start() {
        this.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Countdown.this.tick();
            }
        }, 0L, 20L);
    }
    
    private void tick() {
        this.timeLeft--;
        if (this.onTick != null)
            this.onTick.run();
        
        if (this.timeLeft < 1) {
            Pexel.getScheduler().cancelTask(this.taskId);
            if (this.onEnd != null)
                this.onEnd.run();
        }
    }
    
    /**
     * Pauses countdown. Resume with {@link Countdown#start()}.
     */
    public void pause() {
        Pexel.getScheduler().cancelTask(this.taskId);
    }
    
    /**
     * Resets time left to default value.
     */
    public void reset() {
        this.timeLeft = this.timeLenght;
    }
    
    /**
     * Sets runnable that will be executed when countdown reach zero.
     * 
     * @param onEnd
     *            runnable
     */
    public void setOnEnd(final Runnable onEnd) {
        this.onEnd = onEnd;
    }
    
    /**
     * Sets runnable that will be executed each second.
     * 
     * @param onTick
     *            runnable
     */
    public void setOnTick(final Runnable onTick) {
        this.onTick = onTick;
    }
    
    /**
     * Returns tag of this countdown.
     * 
     * @return tag
     */
    public String getTag() {
        return this.tag;
    }
    
    /**
     * Returns time left in countdown in seconds.
     * 
     * @return time left in seconds
     */
    public int getTimeLeft() {
        return this.timeLeft;
    }
    
    /**
     * Returns lenght of countdown.
     * 
     * @return lenght
     */
    public int getLenght() {
        return this.timeLenght;
    }
}
