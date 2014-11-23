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

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.network.ProxiedPlayer;

/**
 * Player interface for different slave implementations.
 */
public abstract class Player extends ProxiedPlayer {
    /**
     * Returns player's {@link Profile}.
     */
    public abstract Profile getProfile();
    
    /**
     * Returns player's {@link MetadataStore}.
     */
    public abstract MetadataStore getMetadata();
    
    /**
     * Returns player's {@link UUID}.
     * 
     * @return player's uuid on network
     */
    public abstract UUID getUUID();
    
    /**
     * Kicks this player from server with specified reason.
     * 
     * @param reason
     *            reason of kick
     */
    public abstract void kick(String reason);
    
    /**
     * Teleports player to spefied location.
     * 
     * @param loc
     *            location to teleport player to
     */
    public abstract void teleport(Location loc);
    
    /**
     * @return
     */
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @return
     */
    public int getEntityId() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * @return
     */
    public double getHealth() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * @return
     */
    public Location getLocation() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param msg
     */
    public void sendMessage(final String msg) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param location
     * @param sound
     * @param volume
     * @param pitch
     */
    public void playSound(final Location location, final Sound sound,
            final float volume, final float pitch) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @return
     */
    public Inventory getInventory() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * @param adventure
     */
    public void setGameMode(final GameMode adventure) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param potionEffect
     */
    public void addPotionEffect(final PotionEffect potionEffect) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param b
     */
    public void setAllowFlight(final boolean b) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param b
     */
    public void setFlying(final boolean b) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param nightVision
     */
    public void removePotionEffect(final PotionEffectType nightVision) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param i
     */
    public void setFoodLevel(final int i) {
        // TODO Auto-generated method stub
        
    }
}
