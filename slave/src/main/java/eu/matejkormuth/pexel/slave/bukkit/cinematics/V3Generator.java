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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

/**
 * Pomocna trieda na generovanie rznych typov pohybov.
 * 
 * @author Mato Kormuth
 * 
 */
public class V3Generator {
    // Mala staticka classa.
    private V3Generator() {
        
    }
    
    /**
     * Vygeneruje ranmce pre rovnomerne rychlu cestu z pos1 do pos2 so specifikovanym pitch a yaw pocas specifikovaneho
     * casu v sekundach a specifikovaneho poctu ramcov za sekundu.
     * 
     * @param pos1
     * @param pos2
     * @param fps
     * @param seconds
     * @param yaw
     * @param pitch
     * @return
     */
    public static List<V3CameraFrame> line(final Vector pos1, final Vector pos2,
            final int fps, final int seconds, final float yaw, final float pitch) {
        List<V3CameraFrame> frameList = new ArrayList<V3CameraFrame>();
        int frameCount = fps * seconds;
        // Vypocitaj vzdialenosti.
        Vector positionDifference = pos2.subtract(pos1);
        // Zisti posunutia (prirastky).
        Vector frameChange = new Vector(positionDifference.getX() / frameCount,
                positionDifference.getY() / frameCount, positionDifference.getZ()
                        / frameCount);
        
        for (int frameNum = 0; frameNum < frameCount; frameNum++) {
            // Zvacsi pos1 o prirastok.
            pos1.add(frameChange);
            // Pridaj ramec.
            frameList.add(new V3CameraFrame(pos1.getX(), pos1.getY(), pos1.getZ(), yaw,
                    pitch, false));
        }
        return frameList;
    }
    
    public static List<V3CameraFrame> lineLookAt(final Vector pos1, final Vector pos2,
            final Vector lookAt, final int fps, final int seconds) {
        List<V3CameraFrame> frameList = new ArrayList<V3CameraFrame>();
        int frameCount = fps * seconds;
        // Vypocitaj vzdialenosti.
        Vector positionDifference = pos2.subtract(pos1);
        // Zisti posunutia (prirastky).
        Vector frameChange = new Vector(positionDifference.getX() / frameCount,
                positionDifference.getY() / frameCount, positionDifference.getZ()
                        / frameCount);
        
        for (int frameNum = 0; frameNum < frameCount; frameNum++) {
            // Zvacsi pos1 o prirastok.
            pos1.add(frameChange);
            float pitch = 0;
            float yaw = (float) -((Math.atan2(pos1.getX() - lookAt.getX(), pos1.getZ()
                    - lookAt.getZ()) * 180 / Math.PI) - 180);
            // Pridaj ramec.
            frameList.add(new V3CameraFrame(pos1.getX(), pos1.getY(), pos1.getZ(), yaw,
                    pitch, false));
        }
        return frameList;
    }
    
    /**
     * Vygeneruje ramce pre pohyb po kruznici a lookAt specifikovany.
     * 
     * @param center
     * @param lookAt
     * @param radius
     * @param fps
     * @param seconds
     * @param speed
     * @return
     */
    public static List<V3CameraFrame> flyInCirleLookAt(final Vector center,
            final Vector lookAt, final int radius, final int fps, final int seconds,
            final float speed) {
        List<V3CameraFrame> frameList = new ArrayList<V3CameraFrame>();
        int frameCount = fps * seconds;
        for (int frameNum = 0; frameNum < frameCount; frameNum++) {
            // Vypocitaj X a Z, Y zostava rovnake.
            double x = center.getX() + Math.sin(frameNum * speed) * radius;
            double z = center.getZ() + Math.cos(frameNum * speed) * radius;
            // Vypocitaj pitch a yaw.
            float pitch = 0;
            float yaw = (float) -((Math.atan2(x - lookAt.getX(), z - lookAt.getZ()) * 180 / Math.PI) - 180);
            // Pridaj ramec.
            frameList.add(new V3CameraFrame(x, center.getY(), z, yaw, pitch, false));
        }
        return frameList;
    }
    
    public static List<V3CameraFrame> splinePath(final List<Vector> points) {
        List<V3CameraFrame> frameList = new ArrayList<V3CameraFrame>();
        
        return frameList;
    }
}
