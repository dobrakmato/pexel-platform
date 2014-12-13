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

/**
 * Class that represents location.
 */
@XmlType
@JsonType
public class Location {
    private double x;
    private double y;
    private double z;
    private float  yaw;
    private float  pitch;
    private UUID   world;
    
    public Location(final UUID world) {
        this.world = world;
    }
    
    public Location(final double x, final double y, final double z, final UUID world) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Location(final double x, final double y, final double z, final float yaw,
            final float pitch, final UUID world) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (31 * hash + this.x);
        hash = (int) (31 * hash + this.y);
        hash = (int) (31 * hash + this.z);
        hash = (int) (31 * hash + this.yaw);
        hash = (int) (31 * hash + this.pitch);
        hash = 31 * hash + this.world.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) { return false; }
        
        if (obj instanceof Location) { return this.x == ((Location) obj).x
                && this.y == ((Location) obj).y && this.z == ((Location) obj).z
                && this.yaw == ((Location) obj).yaw
                && this.pitch == ((Location) obj).pitch
                && this.world == ((Location) obj).world; }
        return false;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public UUID getWorld() {
        return this.world;
    }
    
    public void setWorld(final UUID world) {
        this.world = world;
    }
    
}
