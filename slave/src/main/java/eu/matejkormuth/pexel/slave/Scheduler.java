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
package eu.matejkormuth.pexel.slave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents scheduler.
 */
public class Scheduler implements TickHandler {
    private final List<ScheduledTask> tasks = new ArrayList<Scheduler.ScheduledTask>();
    
    public ScheduledTask each(final long interval, final Runnable runnable) {
        ScheduledTask task = new ScheduledTask(runnable, interval);
        this.tasks.add(task);
        return task;
    }
    
    public ScheduledTask each(final long interval, final long delay,
            final Runnable runnable) {
        ScheduledTask task = new ScheduledTask(runnable, interval, delay);
        this.tasks.add(task);
        return task;
    }
    
    @Override
    public void tick() {
        long time = System.currentTimeMillis();
        for (Iterator<ScheduledTask> iterator = this.tasks.iterator(); iterator.hasNext();) {
            ScheduledTask task = iterator.next();
            if (task.nextRun == -1) {
                iterator.remove();
            }
            
            if (task.nextRun < time) {
                task.fire();
                task.nextRun = time + task.interval;
            }
        }
    }
    
    public static class ScheduledTask {
        private final long     interval;
        private long           nextRun = 0;
        private final Runnable task;
        
        public ScheduledTask(final Runnable runnable, final long interval) {
            this.interval = interval;
            this.task = runnable;
            this.nextRun = System.currentTimeMillis();
        }
        
        public ScheduledTask(final Runnable runnable, final long interval,
                final long delay) {
            this.interval = interval * 20 * 1000;
            this.task = runnable;
            this.nextRun = System.currentTimeMillis() + delay * 20 * 1000;
        }
        
        public void fire() {
            try {
                this.task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void cancel() {
            this.nextRun = -1;
        }
    }
}
