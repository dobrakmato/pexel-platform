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
package eu.matejkormuth.pexel.master;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler
 */
public class Scheduler {
    private final ScheduledExecutorService service;
    
    public Scheduler() {
        this.service = Executors.newScheduledThreadPool(1);
    }
    
    public ScheduledFuture<?> schedule(final Runnable command, final long delay,
            final TimeUnit unit) {
        return this.service.schedule(command, delay, unit);
    }
    
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay,
            final TimeUnit unit) {
        return this.service.schedule(callable, delay, unit);
    }
    
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable command,
            final long initialDelay, final long period, final TimeUnit unit) {
        return this.service.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
    
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command,
            final long initialDelay, final long delay, final TimeUnit unit) {
        return this.service.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }
}
