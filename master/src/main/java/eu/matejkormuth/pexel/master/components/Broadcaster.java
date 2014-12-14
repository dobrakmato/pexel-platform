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
package eu.matejkormuth.pexel.master.components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.master.MasterComponent;

/**
 * Component that periodically broadcasts messages.
 */
public class Broadcaster extends MasterComponent implements Runnable {
    private final List<String>  messages;
    private long                interval        = 60000L;
    private boolean             randomized      = false;
    private final AtomicInteger currentPosition = new AtomicInteger(0);
    
    public Broadcaster(final long interval) {
        this.interval = interval;
        this.messages = new ArrayList<String>();
    }
    
    public void addMessage(final String message) {
        this.messages.add(message);
    }
    
    public void clear() {
        this.messages.clear();
    }
    
    public boolean isRandomized() {
        return this.randomized;
    }
    
    public void setRandomized(final boolean randomized) {
        this.randomized = randomized;
    }
    
    @Override
    public void onEnable() {
        this.getMaster()
                .getScheduler()
                .scheduleAtFixedRate(this, 0L, this.interval, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public void run() {
        if (this.randomized) {
            this.broadcast(this.messages.get(Providers.RANDOM.nextInt(this.messages.size())));
        }
        else {
            if (this.currentPosition.get() == this.messages.size()) {
                this.currentPosition.set(0);
            }
            this.broadcast(this.messages.get(this.currentPosition.get()));
            this.currentPosition.incrementAndGet();
        }
    }
    
    private void broadcast(final String msg) {
        this.getMaster().getProxy().broadcast(msg);
    }
}
