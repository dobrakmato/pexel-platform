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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Location that is serializable.
 * 
 * @author Mato Kormuth
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SerializableLocation implements Serializable {
    private static final long  serialVersionUID = -7108270886650549653L;
    
    @XmlAttribute(name = "x")
    protected double           X;
    @XmlAttribute(name = "y")
    protected double           Y;
    @XmlAttribute(name = "z")
    protected double           Z;
    @XmlAttribute(name = "yaw")
    protected float            yaw;
    @XmlAttribute(name = "pitch")
    protected float            pitch;
    @XmlAttribute(name = "world")
    protected String           worldName;
    // Initialized on first getLocation() call.
    private transient Location location;
    
    public SerializableLocation(final double x, final double y, final double z,
            final float yaw, final float pitch, final String worldName) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.worldName = worldName;
    }
    
    public SerializableLocation(final Location location) {
        this.location = location;
        this.X = location.getX();
        this.Y = location.getY();
        this.Z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
    }
    
    public static final SerializableLocation fromLocation(final Location location) {
        return new SerializableLocation(location);
    }
    
    public Location getLocation() {
        if (this.location == null)
            this.create();
        return this.location;
    }
    
    private void create() {
        this.location = new Location(Bukkit.getWorld(this.worldName), this.X, this.Y,
                this.Z, this.yaw, this.pitch);
    }
}
