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
 * Class that represents vector.
 */
@XmlType
@JsonType
public class Location {
    private Vector3d   vector;
    private float      yaw;
    private float      pitch;
    private final UUID world;
    
    public Location(final UUID world) {
        this.world = world;
    }
    
    public Location(final Vector3d vector, final UUID world) {
        this.vector = vector;
        this.world = world;
    }
    
    public Location(final double x, final double y, final double z, final UUID world) {
        this.world = world;
        this.vector = new Vector3d(x, y, z);
    }
    
    public Location(final double x, final double y, final double z, final float yaw,
            final float pitch, final UUID world) {
        this.world = world;
        this.vector = new Vector3d(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Location(this.vector.getX(), this.vector.getY(), this.vector.getZ(),
                this.yaw, this.pitch, this.world);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.vector.hashCode();
        hash = (int) (31 * hash + this.yaw);
        hash = (int) (31 * hash + this.pitch);
        hash = 31 * hash + this.world.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        
        if (obj == null) { return false; }
        
        if (obj instanceof Location) { return this.vector.equals(((Location) obj).vector)
                && this.yaw == ((Location) obj).yaw
                && this.pitch == ((Location) obj).pitch
                && this.world == ((Location) obj).world; }
        return false;
    }
    
    public double getX() {
        return this.vector.getX();
    }
    
    public double getY() {
        return this.vector.getY();
    }
    
    public double getZ() {
        return this.vector.getZ();
    }
    
    public Vector3d toVector() {
        return this.vector;
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
        return "Location [vector=" + this.vector + ", yaw=" + this.yaw + ", pitch="
                + this.pitch + ", world=" + this.world + "]";
    }
    
    public Location add(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.vector.add(location.toVector()), this.world);
    }
    
    public Location subtract(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.vector.subtract(location.toVector()), this.world);
    }
    
    public Location multiply(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.vector.multiply(location.toVector()), this.world);
    }
    
    public Location divide(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        return new Location(this.vector.divide(location.toVector()), this.world);
    }
    
    public Location add(final double d) {
        return new Location(this.vector.add(d), this.world);
    }
    
    public Location subtract(final double d) {
        return new Location(this.vector.subtract(d), this.world);
    }
    
    public Location multiply(final double d) {
        return new Location(this.vector.multiply(d), this.world);
    }
    
    public Location divide(final double d) {
        return new Location(this.vector.divide(d), this.world);
    }
    
    public Location add(final double x, final double y, final double z) {
        return new Location(this.vector.add(x, y, z), this.world);
    }
    
    public Location subtract(final double x, final double y, final double z) {
        return new Location(this.vector.subtract(x, y, z), this.world);
    }
    
    public Location multiply(final double x, final double y, final double z) {
        return new Location(this.vector.multiply(x, y, z), this.world);
    }
    
    public Location divide(final double x, final double y, final double z) {
        return new Location(this.vector.divide(x, y, z), this.world);
    }
    
    public Location add(final Vector3d amount) {
        return new Location(this.vector.add(amount), this.world);
    }
    
    public Location subtract(final Vector3d amount) {
        return new Location(this.vector.subtract(amount), this.world);
    }
    
    public Location multiply(final Vector3d amount) {
        return new Location(this.vector.multiply(amount), this.world);
    }
    
    public Location divide(final Vector3d amount) {
        return new Location(this.vector.divide(amount), this.world);
    }
    
    public MutableLocation toMutable() {
        return new MutableLocation(this.vector.getX(), this.vector.getY(),
                this.vector.getZ(), this.yaw, this.pitch, this.world);
    }
}
