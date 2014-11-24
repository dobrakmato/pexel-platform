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
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.network.ProxiedPlayer;

/**
 * Player wrapper interface for different slave implementations.
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
     * Returns player's name.
     * 
     * @return player's name
     */
    public abstract String getName();
    
    /**
     * Returns player's entity id on local server.
     * 
     * @return players entity id
     */
    public abstract int getEntityId();
    
    /**
     * Returns player health. Default maximum is 20.
     * 
     * @return current player health
     */
    public abstract double getHealth();
    
    /**
     * Returns current player location.
     * 
     * @return location of player
     */
    //DEBUKKITIZE:
    public abstract Location getLocation();
    
    /**
     * Sends text message to this player.
     * 
     * @param msg
     *            contents of message
     */
    public abstract void sendMessage(final String msg);
    
    /**
     * @param location
     * @param sound
     * @param volume
     * @param pitch
     */
    //DEBUKKITIZE:
    public abstract void playSound(final Location location, final Sound sound,
            final float volume, final float pitch);
    
    /**
     * @return
     */
    //DEBUKKITIZE:
    public abstract Inventory getInventory();
    
    /**
     * @param adventure
     */
    //DEBUKKITIZE:
    public abstract void setGameMode(final GameMode gameMode);
    
    /**
     * @param potionEffect
     */
    //DEBUKKITIZE:
    public abstract void addPotionEffect(final PotionEffect potionEffect);
    
    /**
     * Set's whether the player is allowed to flight.
     * 
     * @param b
     *            true if player should be allowed to flight, else otherwise
     */
    public abstract void setAllowFlight(final boolean allowFlight);
    
    /**
     * Set's whether the player is currently flying.
     * 
     * @param b
     *            true if player should be flying, else otherwise
     */
    public abstract void setFlying(final boolean flying);
    
    /**
     * @param nightVision
     */
    //DEBUKKITIZE:
    public abstract void removePotionEffect(final PotionEffectType potionEffectType);
    
    /**
     * Set's player's food level. Default maximum is 20.
     * 
     * @param i
     *            new food level
     */
    public abstract void setFoodLevel(final int foodLevel);
    
    /**
     * Returns whether the player is online.
     * 
     * @return true if player is online, else otherwise
     */
    public abstract boolean isOnline();
    
    /**
     * Returns whether this player is OP.
     * 
     * @return true if player is OP, else otherwise
     */
    public abstract boolean isOp();
    
    /**
     * Returns player's current world.
     * 
     * @return
     */
    //TODO: Extract from location.
    //DEBUKKITIZE:
    public World getWorld() {
        return this.getLocation().getWorld();
    }
    
    /**
     * @param inventory
     */
    //DEBUKKITIZE:
    public abstract void openInventory(final Inventory inventory);
    
    /**
     * Performs specified command as player.
     * 
     * @param command
     *            command to be executed
     */
    public abstract void performCommand(final String command);
}
