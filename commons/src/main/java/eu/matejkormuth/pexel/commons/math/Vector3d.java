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
package eu.matejkormuth.pexel.commons.math;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import eu.matejkormuth.pexel.commons.annotations.JsonType;

/**
 * Vector that is serializable.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@JsonType
public class Vector3d implements Serializable {
    private static final long serialVersionUID = -5438305359697297397L;
    
    public static Vector3d    ZERO             = new Vector3d(0, 0, 0);
    
    @XmlAttribute(name = "x")
    private final double      x;
    @XmlAttribute(name = "y")
    private final double      y;
    @XmlAttribute(name = "z")
    private final double      z;
    
    /**
     * Constructs new 3 dimensional vector using 3 double co-ordinates.
     * 
     * @param x
     *            x co-ordinate
     * @param y
     *            y co-ordinate
     * @param z
     *            z co-ordinate
     */
    public Vector3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31
                + (int) ((int) this.x ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = hash * 31
                + (int) ((int) this.y ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = hash * 31
                + (int) ((int) this.z ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        if (this == obj) { return true; }
        
        if (obj instanceof Vector3d) {
            return ((Vector3d) obj).x == this.x && ((Vector3d) obj).y == this.y
                    && ((Vector3d) obj).z == this.z;
        }
        else {
            return false;
        }
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Vector3d(this.x, this.y, this.z);
    }
    
    public Vector3d multiply(final double d) {
        return new Vector3d(this.x * d, this.y * d, this.z * d);
    }
    
    public Vector3d multiply(final Vector3d vector) {
        return new Vector3d(this.x * vector.x, this.y * vector.y, this.z * vector.z);
    }
    
    public Vector3d divide(final double d) {
        return new Vector3d(this.x / d, this.y / d, this.z / d);
    }
    
    public Vector3d divide(final Vector3d vector) {
        return new Vector3d(this.x / vector.x, this.y / vector.y, this.z / vector.z);
    }
    
    public Vector3d add(final double d) {
        return new Vector3d(this.x + d, this.y + d, this.z + d);
    }
    
    public Vector3d add(final Vector3d vector) {
        return new Vector3d(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }
    
    public Vector3d subtract(final double d) {
        return new Vector3d(this.x - d, this.y - d, this.z - d);
    }
    
    public Vector3d subtract(final Vector3d vector) {
        return new Vector3d(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    @Override
    public String toString() {
        return "Vector3d [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
    }
}
