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
package eu.matejkormuth.pexel.commons.data;

import java.util.UUID;

/**
 * Interface that specifies player's profile.
 */
public interface Profile {
    /**
     * Returns id of profile in database.
     */
    public long getId();
    
    /**
     * Returns minecraft UUID of profile.
     */
    public UUID getUUID();
    
    /**
     * Returns last known name of this player.
     */
    public String getLastKnownName();
    
    /**
     * Returns amount of XP (expierence) on the network.
     */
    public int getXP();
    
    /**
     * Returns amount of coins on the network.
     */
    public int getCoins();
    
    /**
     * Returns amount of premium (paid) coins on the network.
     */
    public int getPremiumCoins();
}
