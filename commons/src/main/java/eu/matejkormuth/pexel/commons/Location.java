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
package eu.matejkormuth.pexel.commons;

import java.util.UUID;

import javax.xml.bind.annotation.XmlType;

import eu.matejkormuth.pexel.commons.annotations.JsonType;
import eu.matejkormuth.pexel.commons.math.Vector3d;

/**
 * Class that represents location.
 */
@XmlType
@JsonType
public class Location {
    private Vector3d   location;
    private float      yaw;
    private float      pitch;
    private final UUID world;
    
    public Location(final UUID world) {
        this.world = world;
    }
    
    public Location(final Vector3d vector, final UUID world) {
        this.location = vector;
        this.world = world;
    }
    
    public Location(final double x, final double y, final double z, final UUID world) {
        this.world = world;
        this.location = new Vector3d(x, y, z);
    }
    
    public Location(final double x, final double y, final double z, final float yaw,
            final float pitch, final UUID world) {
        this.world = world;
        this.location = new Vector3d(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Location(this.location.getX(), this.location.getY(),
                this.location.getZ(), this.yaw, this.pitch, this.world);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.location.hashCode();
        hash = (int) (31 * hash + this.yaw);
        hash = (int) (31 * hash + this.pitch);
        hash = 31 * hash + this.world.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        
        if (obj == null) { return false; }
        
        if (obj instanceof Location) { return this.location.equals(((Location) obj).location)
                && this.yaw == ((Location) obj).yaw
                && this.pitch == ((Location) obj).pitch
                && this.world == ((Location) obj).world; }
        return false;
    }
    
    public double getX() {
        return this.location.getX();
    }
    
    public double getY() {
        return this.location.getY();
    }
    
    public double getZ() {
        return this.location.getZ();
    }
    
    public Vector3d toVector() {
        return this.location;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public UUID getWorld() {
        return this.world;
    }
    
    @Override
    public String toString() {
        return "Location [location=" + this.location + ", yaw=" + this.yaw + ", pitch="
                + this.pitch + ", world=" + this.world + "]";
    }
    
    public Location add(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.location.add(location.toVector()), this.world);
    }
    
    public Location subtract(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.location.subtract(location.toVector()), this.world);
    }
    
    public Location multiply(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.location.multiply(location.toVector()), this.world);
    }
    
    public Location divide(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.location.divide(location.toVector()), this.world);
    }
    
    public Location add(final double d) {
        return new Location(this.location.add(d), this.world);
    }
    
    public Location subtract(final double d) {
        return new Location(this.location.subtract(d), this.world);
    }
    
    public Location multiply(final double d) {
        return new Location(this.location.multiply(d), this.world);
    }
    
    public Location divide(final double d) {
        return new Location(this.location.divide(d), this.world);
    }
}
