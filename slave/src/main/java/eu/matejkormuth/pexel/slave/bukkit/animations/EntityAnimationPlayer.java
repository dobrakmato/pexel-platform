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

import org.bukkit.entity.Entity;

import eu.matejkormuth.pexel.slave.bukkit.util.BukkitTimer;

public class EntityAnimationPlayer implements Runnable {
    private final Animation animation;
    private int             currentFrame = 0;
    private int             frameCount   = 0;
    private BukkitTimer     timer;
    private final Entity    entity;
    private boolean         repeating    = false;
    
    public EntityAnimationPlayer(final Animation animation, final Entity entity,
            final boolean repeating) {
        this.repeating = repeating;
        this.animation = animation;
        this.entity = entity;
        this.frameCount = animation.getFrameCount();
    }
    
    public void play() {
        this.timer = new BukkitTimer(1, this);
        this.timer.start();
    }
    
    private void animate() {
        if (this.entity.isDead()) {
            this.timer.stop();
        }
        
        if (this.currentFrame < this.frameCount) {
            this.animation.getFrame(this.currentFrame).play(this.entity.getLocation());
            this.currentFrame++;
        }
        else {
            if (!this.repeating) {
                this.timer.stop();
            }
            else {
                this.currentFrame = 0;
            }
        }
    }
    
    @Override
    public void run() {
        this.animate();
    }
}
