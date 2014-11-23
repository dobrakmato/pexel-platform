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

import java.io.DataOutputStream;
import java.io.IOException;

import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3Meta;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.V3MetaType;

/**
 * @author Mato Kormuth
 * 
 */
public class V3MetaExplosion implements V3Meta {
    private double posX;
    private double posY;
    private double posZ;
    
    @Override
    public V3MetaType getMetaType() {
        return V3MetaType.V3MetaExplosion;
    }
    
    @Override
    public void writeMeta(final DataOutputStream stream) throws IOException {
        
    }
    
    @Override
    public int getType() {
        return 0;
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
    
}
