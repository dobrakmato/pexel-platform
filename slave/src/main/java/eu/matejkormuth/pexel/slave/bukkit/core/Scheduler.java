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
package eu.matejkormuth.pexel.slave.bukkit.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import eu.matejkormuth.pexel.slave.boot.PexelSlaveBukkitPlugin;

/**
 * Scheduler class for pexel to be used when porting from Bukkit to Sponge..
 */
public class Scheduler {
    private final List<ScheduledTask> tasks = new ArrayList<ScheduledTask>();
    private long                      elapsed;
    private ArrayList<ScheduledTask>  temptasks;
    
    public Scheduler() {
        Log.partEnable("Scheduler");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                PexelSlaveBukkitPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Scheduler.this.tick();
                    }
                }, 1L, 0L);
    }
    
    public int scheduleSyncRepeatingTask(final Runnable runnable, final long delay,
            final long interval) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(
                PexelSlaveBukkitPlugin.getInstance(), runnable, delay, interval);
    }
    
    public int scheduleSyncDelayedTask(final Runnable runnable, final long delay) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(
                PexelSlaveBukkitPlugin.getInstance(), runnable, delay);
    }
    
    public void delay(final long delay, final Runnable runnable) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                PexelSlaveBukkitPlugin.getInstance(), runnable, delay);
    }
    
    public ScheduledTask each(final long period, final Runnable runnable) {
        ScheduledTask task = new ScheduledTask(period, runnable);
        this.tasks.add(task);
        return task;
    }
    
    public void cancel(final ScheduledTask task) {
        this.tasks.remove(task);
    }
    
    public void cancelTask(final int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }
    
    public void tick() {
        this.temptasks = new ArrayList<ScheduledTask>(this.tasks);
        for (ScheduledTask task : this.temptasks) {
            if (this.elapsed % task.period == 0) {
                try {
                    task.runnable.run();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        this.elapsed++;
    }
    
    public static final class ScheduledTask {
        public ScheduledTask(final long period2, final Runnable runnable2) {
            this.period = period2;
            this.runnable = runnable2;
        }
        
        public final Runnable runnable;
        public final long     period;
    }
}
