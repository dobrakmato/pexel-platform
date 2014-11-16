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

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class used for synchronizing network with minecraft server instance.
 */
public class Sync {
    /**
     * Maximum amount of tasks, that can be executed on one server tick.
     */
    public static final int       MAX_TASKS_PER_TICK = 128;
    private final Runnable        onTick;
    private final Queue<Runnable> tasks              = new LinkedBlockingQueue<Runnable>();
    private int                   lastTasksCount     = 0;
    
    public Sync() {
        this.onTick = new Runnable() {
            private long ticks = 0;
            
            @Override
            public void run() {
                Sync.this.onTick(this.ticks);
                this.ticks++;
            }
        };
    }
    
    public Runnable getOnTick() {
        return this.onTick;
    }
    
    public void addTask(final Runnable runnable) {
        this.tasks.add(runnable);
    }
    
    protected void onTick(final long tick) {
        this.lastTasksCount = 0;
        Runnable task;
        while ((task = this.tasks.peek()) != null
                && this.lastTasksCount < Sync.MAX_TASKS_PER_TICK) {
            task.run();
        }
        
        if (this.lastTasksCount > Sync.MAX_TASKS_PER_TICK - 20) {
            // TODO: Send debug requrest about that this slave got too much tasks.
        }
    }
}
