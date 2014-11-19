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
package eu.matejkormuth.pexel.slave;

import java.util.UUID;

import eu.matejkormuth.pexel.commons.Commitable;
import eu.matejkormuth.pexel.commons.MetadataStore;
import eu.matejkormuth.pexel.commons.Metadatable;
import eu.matejkormuth.pexel.commons.structures.Profile;

/**
 * Class that represents profile of player and implementes {@link Profile} and {@link Commitable}.
 */
public class PlayerProfile implements Profile, Commitable, Metadatable {
    private long          id;
    private String        uuid;
    private String        lastKnownName;
    private int           xp;
    private int           coins;
    private int           premiumCoins;
    private MetadataStore metadata;
    
    @Override
    public long getId() {
        return this.id;
    }
    
    @Override
    public UUID getUUID() {
        return UUID.fromString(this.uuid);
    }
    
    @Override
    public String getLastKnownName() {
        return this.lastKnownName;
    }
    
    @Override
    public int getXP() {
        return this.xp;
    }
    
    @Override
    public int getCoins() {
        return this.coins;
    }
    
    @Override
    public int getPremiumCoins() {
        return this.premiumCoins;
    }
    
    @Override
    public void commit() {
        
    }
    
    @Override
    public void setMetadata(final String key, final String value) {
        if (this.metadata == null) {
            this.metadata = MetadataStore.create(key, value);
        }
    }
    
    @Override
    public String getMetadata(final String key) {
        if (this.metadata == null) {
            //TODO: this.requestMetadata();
        }
        return this.metadata.getMetadata(key);
    }
}
