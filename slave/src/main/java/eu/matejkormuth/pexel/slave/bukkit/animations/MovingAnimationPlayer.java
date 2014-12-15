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
package eu.matejkormuth.pexel.slave.bukkit.animations;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.MovingObject;
import eu.matejkormuth.pexel.commons.animations.Animation;
import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.core.Scheduler.ScheduledTask;

public class MovingAnimationPlayer extends MovingObject implements Runnable {
    protected Animation     animation;
    protected int           currentFrame = 0;
    protected int           frameCount   = 0;
    protected ScheduledTask task;
    protected boolean       repeating    = false;
    
    public MovingAnimationPlayer(final Animation animation, final Location startLoc,
            final boolean repeating) {
        this.repeating = repeating;
        this.animation = animation;
        this.location = startLoc.toMutable();
        this.frameCount = animation.getFrameCount();
    }
    
    public void play() {
        this.task = Pexel.getScheduler().each(1L, this);
    }
    
    public Animation getAnimation() {
        return this.animation;
    }
    
    public int getCurrentFrame() {
        return this.currentFrame;
    }
    
    public boolean isRepeating() {
        return this.repeating;
    }
    
    protected void animateFrame() {
        if (this.currentFrame < this.frameCount) {
            this.animation.getFrame(this.currentFrame).play(this.location);
            this.currentFrame++;
        }
        else {
            if (!this.repeating) {
                Pexel.getScheduler().cancel(this.task);
            }
            else {
                this.currentFrame = 0;
            }
        }
    }
    
    @Override
    public void run() {
        this.animateFrame();
    }
}
