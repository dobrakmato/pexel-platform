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
public class V3MetaEntityDamage implements V3Meta {
    private static final int TYPEID = 2;
    
    private final long       internalId;
    private final float      damage;
    
    /**
     * @param internalId
     * @param damage
     */
    public V3MetaEntityDamage(final long internalId, final float damage) {
        this.internalId = internalId;
        this.damage = damage;
    }
    
    @Override
    public void writeMeta(final DataOutputStream stream) throws IOException {
        stream.writeLong(this.internalId);
        stream.writeFloat(this.damage);
    }
    
    public static V3MetaEntityDamage readMeta(final DataInputStream stream)
            throws IOException {
        long internalId = stream.readLong();
        float damage = stream.readFloat();
        
        return new V3MetaEntityDamage(internalId, damage);
    }
    
    @Override
    public int getType() {
        return V3MetaEntityDamage.TYPEID;
    }
    
    @Override
    public V3MetaType getMetaType() {
        return V3MetaType.V3MetaEntityDamage;
    }
    
    /**
     * @return the internalId
     */
    public long getInternalId() {
        return this.internalId;
    }
    
    /**
     * @return the damage
     */
    public float getDamage() {
        return this.damage;
    }
}