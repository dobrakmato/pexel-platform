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
package eu.matejkormuth.pexel.slave.bukkit.cinematics;

import org.bukkit.Location;

/**
 * @author Mato Kormuth
 * 
 */
public class V3ClipSmoother {
    public static void smoothCompiledClip(final String path, final int a)
            throws Exception {
        V3CameraClip clip = V3CompiledReader.loadFile(path);
        
        clip = V3ClipSmoother.smooth(clip, a);
        
        V3CompiledWriter writer = V3CompiledWriter.createFile(path);
        writer.writeClip(clip);
        writer.close();
    }
    
    public static V3CameraClip smoothClip(final V3CameraClip clip, final int a) {
        return V3ClipSmoother.smooth(clip, a);
    }
    
    /**
     * @param clip
     * @param i
     * @return
     */
    private static V3CameraClip smooth(final V3CameraClip clip, final int a) {
        V3CameraClip newClip = new V3CameraClip();
        
        for (int i = 0; i < clip.frames.size(); i++) {
            V3CameraFrame currentFrame = clip.frames.get(i);
            V3CameraFrame nextFrame = clip.frames.get(i + 1);
            
            newClip.addFrame(currentFrame);
            
            // Smooth movement (X,Y,Z)
            double x = 0;
            double y = 0;
            double z = 0;
            
            // Smooth rotation (Pitch, Yaw)
            float newYaw = (currentFrame.getCameraLocation().getYaw() + nextFrame.getCameraLocation().getYaw()) / 2;
            float newPitch = (currentFrame.getCameraLocation().getPitch() + nextFrame.getCameraLocation().getPitch()) / 2;
            
            Location newLoc = new Location(null, x, y, z, newYaw, newPitch);
            V3CameraFrame newFrame = new V3CameraFrame(newLoc, false);
            
            // Add new frame.
            newClip.addFrame(newFrame);
        }
        
        return newClip;
    }
}
