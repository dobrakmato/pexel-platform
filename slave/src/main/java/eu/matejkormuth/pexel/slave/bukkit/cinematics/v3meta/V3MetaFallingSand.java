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

import org.bukkit.Material;

import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3Meta;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3MetaType;

/**
 * @author Mato Kormuth
 * 
 */
public class V3MetaFallingSand implements V3Meta {
    private final double   posX;
    private final double   posY;
    private final double   posZ;
    private final double   velX;
    private final double   velY;
    private final double   velZ;
    private final Material material;
    
    /**
     * @param posX
     * @param posY
     * @param posZ
     * @param velX
     * @param velY
     * @param velZ
     * @param material
     */
    public V3MetaFallingSand(final double posX, final double posY, final double posZ,
            final double velX, final double velY, final double velZ,
            final Material material) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.material = material;
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void writeMeta(final DataOutputStream stream) throws IOException {
        stream.writeDouble(this.posX);
        stream.writeDouble(this.posY);
        stream.writeDouble(this.posZ);
        stream.writeDouble(this.velX);
        stream.writeDouble(this.velY);
        stream.writeDouble(this.velZ);
        stream.writeInt(this.material.getId());
    }
    
    @SuppressWarnings("deprecation")
    public static V3MetaFallingSand readMeta(final DataInputStream stream)
            throws IOException {
        double posX = stream.readDouble();
        double posY = stream.readDouble();
        double posZ = stream.readDouble();
        double velX = stream.readDouble();
        double velY = stream.readDouble();
        double velZ = stream.readDouble();
        Material material = Material.getMaterial(stream.readInt());
        
        return new V3MetaFallingSand(posX, posY, posZ, velX, velY, velZ, material);
    }
    
    @Override
    public int getType() {
        return 8;
    }
    
    @Override
    public V3MetaType getMetaType() {
        return V3MetaType.V3MetaFallingSand;
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
     * @return the velX
     */
    public double getVelX() {
        return this.velX;
    }
    
    /**
     * @return the velY
     */
    public double getVelY() {
        return this.velY;
    }
    
    /**
     * @return the velZ
     */
    public double getVelZ() {
        return this.velZ;
    }
    
    /**
     * @return the material
     */
    public Material getMaterial() {
        return this.material;
    }
    
}
