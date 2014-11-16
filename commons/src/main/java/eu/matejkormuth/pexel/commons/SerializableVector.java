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

import org.bukkit.util.Vector;

/**
 * Vector that is serializable.
 * 
 * @author Mato Kormuth
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SerializableVector extends Vector implements Serializable {
    private static final long serialVersionUID = -5438305359697297397L;
    @XmlAttribute(name = "x")
    protected double          x;
    @XmlAttribute(name = "y")
    protected double          y;
    @XmlAttribute(name = "z")
    protected double          z;
    
    public SerializableVector(final Vector vector) {
        super(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public SerializableVector(final int x, final int y, final int z) {
        super(x, y, z);
    }
    
    public SerializableVector(final double x, final double y, final double z) {
        super(x, y, z);
    }
    
    public SerializableVector(final float x, final float y, final float z) {
        super(x, y, z);
    }
}
