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

import eu.matejkormuth.pexel.commons.data.Profile;

/**
 *
 */
public class SlaveProfile implements Profile {
    protected long   cached_id;
    protected UUID   cached_uuid;
    protected String cached_lastKnownName;
    protected int    cached_xp;
    protected int    cached_coins;
    protected int    cached_premiumConins;
    
    @Override
    public long getId() {
        return this.cached_id;
    }
    
    @Override
    public UUID getUUID() {
        return this.cached_uuid;
    }
    
    @Override
    public String getLastKnownName() {
        return this.cached_lastKnownName;
    }
    
    @Override
    public int getXP() {
        return this.cached_xp;
    }
    
    @Override
    public int getCoins() {
        return this.cached_coins;
    }
    
    @Override
    public int getPremiumCoins() {
        return this.cached_premiumConins;
    }
    
}
