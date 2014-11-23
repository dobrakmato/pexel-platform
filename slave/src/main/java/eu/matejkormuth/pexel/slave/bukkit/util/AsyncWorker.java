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

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import eu.matejkormuth.pexel.slave.bukkit.core.Log;

/**
 * Async worker for things like network stuff, etc...
 * 
 * @author Mato Kormuth
 * 
 */
public class AsyncWorker implements Runnable {
    /**
     * Tasks that should be done.
     */
    private final Queue<Runnable> tasks   = new ArrayBlockingQueue<Runnable>(50);
    /**
     * List of worker threads.
     */
    private final List<Thread>    workers = new ArrayList<Thread>(4);
    private boolean               enabled = false;
    private final int             workersCount;
    
    /**
     * Creates new object with specified amount of wokrker threads.
     * 
     * @param workersCount
     *            count of workers
     */
    public AsyncWorker(final int workersCount) {
        Log.partEnable("AyncWorker");
        this.workersCount = workersCount;
    }
    
    public void start() {
        this.enabled = true;
        for (int i = 0; i < this.workersCount; i++) {
            Log.info("Setting up worker #" + (i + 1) + "...");
            this.workers.add(new Thread(this));
            this.workers.get(this.workers.size() - 1).setName("AsyncWorker-" + (i + 1));
            this.workers.get(this.workers.size() - 1).start();
        }
    }
    
    /**
     * Adds specified task to list. Taks should be executed not later then 200 ms.
     * 
     * @param runnable
     *            taks to be executed from other thread.
     */
    public void addTask(final Runnable runnable) {
        this.tasks.add(runnable);
    }
    
    /**
     * Shutdowns the workers and the logic.
     */
    public void shutdown() {
        Log.partDisable("AsyncWorker");
        this.enabled = false;
    }
    
    @Override
    public void run() {
        while (this.enabled) {
            Runnable r = this.tasks.poll();
            if (r != null)
                try {
                    r.run();
                } catch (Exception ex) {
                    Log.warn("[AsyncWorker] Task generated: " + ex.getMessage());
                }
            else
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
        }
        Log.info(Thread.currentThread().getName() + " died.");
    }
}
