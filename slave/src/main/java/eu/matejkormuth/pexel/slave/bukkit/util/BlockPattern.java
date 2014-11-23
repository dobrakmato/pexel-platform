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

import org.bukkit.Location;
import org.bukkit.material.MaterialData;

/**
 * Class used for detecting block patterns.
 */
public class BlockPattern {
    private final MaterialData[][][] pattern;
    private int                      anchorX;
    private int                      anchorY;
    private int                      anchorZ;
    
    /**
     * Creates new instance of BlockPattern with specified MaterialData pattern in x, y, z coordinate order.
     * 
     * @param pattern
     *            pattern of material data in order [x][y][z]
     */
    public BlockPattern(final MaterialData[][][] pattern) {
        this.pattern = pattern;
    }
    
    /**
     * Sets relative locaton of anchor block.
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param z
     *            z-coordinate
     */
    public void setAnchor(final int x, final int y, final int z) {
        this.anchorX = x;
        this.anchorY = y;
        this.anchorZ = z;
    }
    
    /**
     * Checks for match on specified lcoation.
     * 
     * @param location
     *            location of nachor block
     * @return <b>true</b> if match found, <b>false</b> otherwise
     */
    public boolean match(final Location location) {
        for (int x = 0; x < this.pattern.length; x++) {
            for (int y = 0; y < this.pattern[0].length; y++) {
                for (int z = 0; z < this.pattern[0][0].length; z++) {
                    int absX = location.getBlockX() - this.anchorX + x;
                    int absY = location.getBlockY() - this.anchorY + y;
                    int absZ = location.getBlockZ() - this.anchorZ + z;
                    
                    if (this.pattern[x][y][z] != null) {
                        if (!location.getWorld().getBlockAt(absX, absY, absZ).equals(
                                this.pattern[x][y][z])) { return false; }
                    }
                }
            }
        }
        return true;
    }
}
