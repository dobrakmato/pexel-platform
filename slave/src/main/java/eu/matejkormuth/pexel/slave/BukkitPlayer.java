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

import org.bukkit.Location;

import eu.matejkormuth.pexel.commons.MetadataStore;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.data.Profile;

/**
 * Bukkit compactibile implementation of player.
 */
public class BukkitPlayer extends Player {
    org.bukkit.entity.Player internal;
    
    public BukkitPlayer(final org.bukkit.entity.Player player) {
        this.internal = player;
    }
    
    @Override
    public Profile getProfile() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void teleport(final Location loc) {
        this.internal.teleport(loc);
    }
    
    @Override
    public MetadataStore getMetadata() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public UUID getUUID() {
        return this.internal.getUniqueId();
    }
    
    @Override
    public void kick(final String reason) {
        this.internal.kickPlayer(reason);
    }
    
    @Override
    public String getDisplayName() {
        return this.internal.getDisplayName();
    }
}
