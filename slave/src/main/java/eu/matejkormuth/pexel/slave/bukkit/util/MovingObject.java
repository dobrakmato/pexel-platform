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
import org.bukkit.util.Vector;

/**
 * Class that represents moving object.
 */
public abstract class MovingObject {
    protected Location location;
    
    /**
     * Moves object by specified amount.
     * 
     * @param amount
     *            amount
     */
    public void move(final Location amount) {
        this.move(amount.toVector());
    }
    
    /**
     * Moves object by specified amount.
     * 
     * @param amount
     *            amount
     */
    public void move(final Vector amount) {
        this.move(amount.getX(), amount.getY(), amount.getZ());
    }
    
    /**
     * Moves object by specified amount.
     * 
     * @param x
     *            motX
     * @param y
     *            motY
     * @param z
     *            motZ
     */
    public void move(final double x, final double y, final double z) {
        this.location.add(x, y, z);
    }
    
    /**
     * Sets object's location.
     * 
     * @param location
     *            location to set
     */
    protected void setLocation(final Location location) {
        this.location = location;
    }
    
    /**
     * Returns object current location.
     * 
     * @return current location
     */
    public Location getLocation() {
        return this.location;
    }
}
