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
public class V3MetaEntityTeleport implements V3Meta {
    private final double posX;
    private final double posY;
    private final double posZ;
    private final float  yaw;
    private final float  pitch;
    private final long   internalId;
    
    /**
     * @param posX
     * @param posY
     * @param posZ
     * @param yaw
     * @param pitch
     * @param internalId
     */
    public V3MetaEntityTeleport(final double posX, final double posY, final double posZ,
            final float yaw, final float pitch, final long internalId) {
        super();
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.internalId = internalId;
    }
    
    @Override
    public void writeMeta(final DataOutputStream stream) throws IOException {
        stream.writeDouble(this.posX);
        stream.writeDouble(this.posY);
        stream.writeDouble(this.posZ);
        stream.writeFloat(this.yaw);
        stream.writeFloat(this.pitch);
        stream.writeLong(this.internalId);
    }
    
    public static V3MetaEntityTeleport readMeta(final DataInputStream stream)
            throws IOException {
        double x = stream.readDouble();
        double y = stream.readDouble();
        double z = stream.readDouble();
        float yaw = stream.readFloat();
        float pitch = stream.readFloat();
        long internalId = stream.readLong();
        
        return new V3MetaEntityTeleport(x, y, z, yaw, pitch, internalId);
    }
    
    @Override
    public int getType() {
        return 3;
    }
    
    @Override
    public V3MetaType getMetaType() {
        return V3MetaType.V3MetaEntityTeleport;
    }
    
    /**
     * @return the posX
     */
    public double getPosX() {
        return this.posX;
    }
    
    /**
     * @return the posY
     */
    public double getPosY() {
        return this.posY;
    }
    
    /**
     * @return the posZ
     */
    public double getPosZ() {
        return this.posZ;
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
