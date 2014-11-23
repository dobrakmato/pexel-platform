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
package eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3Meta;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3MetaType;

/**
 * @author Mato Kormuth
 * 
 */
public class V3MetaEntityMove implements V3Meta {
    private final double movX;
    private final double movY;
    private final double movZ;
    private final float  yaw;
    private final float  pitch;
    private final long   internalId;
    
    /**
     * @param movX
     * @param movY
     * @param movZ
     * @param yaw
     * @param pitch
     * @param internalId
     */
    public V3MetaEntityMove(final double movX, final double movY, final double movZ,
            final float yaw, final float pitch, final long internalId) {
        super();
        this.movX = movX;
        this.movY = movY;
        this.movZ = movZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.internalId = internalId;
    }
    
    @Override
    public V3MetaType getMetaType() {
        return V3MetaType.V3MetaEntityMove;
    }
    
    @Override
    public void writeMeta(final DataOutputStream stream) throws IOException {
        stream.writeDouble(this.movX);
        stream.writeDouble(this.movY);
        stream.writeDouble(this.movZ);
        stream.writeFloat(this.yaw);
        stream.writeFloat(this.pitch);
        stream.writeLong(this.internalId);
    }
    
    public static V3MetaEntityMove readMeta(final DataInputStream stream)
            throws IOException {
        double x = stream.readDouble();
        double y = stream.readDouble();
        double z = stream.readDouble();
        float yaw = stream.readFloat();
        float pitch = stream.readFloat();
        long internalId = stream.readLong();
        
        return new V3MetaEntityMove(x, y, z, yaw, pitch, internalId);
    }
    
    @Override
    public int getType() {
        return 10;
    }
    
    /**
     * @return the movX
     */
    public double getMovX() {
        return this.movX;
    }
    
    /**
     * @return the movY
     */
    public double getMovY() {
        return this.movY;
    }
    
    /**
     * @return the movZ
     */
    public double getMovZ() {
        return this.movZ;
    }
    
    /**
     * @return the yaw
     */
    public float getYaw() {
        return this.yaw;
    }
    
    /**
     * @return the pitch
     */
    public float getPitch() {
        return this.pitch;
    }
    
    /**
     * @return the internalId
     */
    public long getInternalId() {
        return this.internalId;
    }
    
}
