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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ParticleAnimation implements Animation, Serializable {
    private static final long              serialVersionUID = 5147694021142531376L;
    
    private final ArrayList<ParticleFrame> frames           = new ArrayList<ParticleFrame>();
    private final int                      framerate        = 20;
    
    @Override
    public ParticleFrame getFrame(final int index) {
        return this.frames.get(index);
    }
    
    @Override
    public int getFramerate() {
        return this.framerate;
    }
    
    @Override
    public int getFrameCount() {
        return this.frames.size();
    }
    
    public void addFrame(final ParticleFrame frame) {
        this.frames.add(frame);
    }
    
    public void addFrames(final Collection<? extends ParticleFrame> frames) {
        this.frames.addAll(frames);
    }
}
