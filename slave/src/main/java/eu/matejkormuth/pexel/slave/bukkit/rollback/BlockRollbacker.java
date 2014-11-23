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
package eu.matejkormuth.pexel.slave.bukkit.rollback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * Block rollbacker.
 * 
 * @author Mato Kormuth
 * 
 */
public class BlockRollbacker {
    //List of changes.
    private final List<BlockChange> changes           = new ArrayList<BlockChange>();
    //List of location changes.
    private final List<Location>    changeLocations   = new ArrayList<Location>();
    //Bukkit task id.
    private int                     taskId            = 0;
    //Runnable to be run after async rollback.
    private Runnable                onFinished;
    //Max block changes per one game tick.
    private int                     maxChangesPerTick = 128;
    
    /**
     * Registers a change to this rollbacker.
     * 
     * @param change
     */
    public void addChange(final BlockChange change) {
        if (!this.changeLocations.contains(change.getLocation())) {
            this.changes.add(change);
            this.changeLocations.add(change.getLocation());
        }
    }
    
    /**
     * Reverts all registered changes right after call of this function. (Not recomended)
     */
    public void rollback() {
        for (Iterator<BlockChange> iterator = this.changes.iterator(); iterator.hasNext();) {
            BlockChange change = iterator.next();
            change.applyRollback();
            this.changeLocations.remove(change.getLocation());
            iterator.remove();
        }
    }
    
    /**
     * Starts a task for rollbacking with default (128) amount of blocks reverted in one tick.
     * 
     * @param onFinished
     *            runnable, that should be called after the rollback is done.
     */
    public void rollbackAsync(final Runnable onFinished) {
        this.onFinished = onFinished;
        this.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(new Runnable() {
            @Override
            public void run() {
                BlockRollbacker.this.doRollback();
            }
        }, 0L, 1L);
    }
    
    /**
     * Starts a task for rollbacking with specified amount of blocks reverted in one tick.
     * 
     * @param onFinished
     *            runnable, that should be called after the rollback is done.
     */
    public void rollbackAsync(final Runnable onFinished, final int maxIterations) {
        this.maxChangesPerTick = maxIterations;
        this.rollbackAsync(onFinished);
    }
    
    /**
     * Performs one rollback (rollbacks this.maxChangesPerTick blocks).
     */
    private void doRollback() {
        if (this.changes.size() == 0) {
            Pexel.getScheduler().cancelTask(this.taskId);
            if (this.onFinished != null)
                this.onFinished.run();
        }
        else {
            int count = 0;
            for (Iterator<BlockChange> iterator = this.changes.iterator(); iterator.hasNext();) {
                if (count > this.maxChangesPerTick)
                    break;
                else {
                    BlockChange bc = iterator.next();
                    this.changeLocations.remove(bc.getLocation());
                    bc.applyRollback();
                    iterator.remove();
                }
                count++;
            }
        }
    }
}
