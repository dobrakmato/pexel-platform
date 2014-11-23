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
package eu.matejkormuth.pexel.slave.bukkit.areas;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.CuboidRegion;
import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.core.Updatable;
import eu.matejkormuth.pexel.slave.bukkit.core.UpdatedParts;

/**
 * Lobby is protected area with some special functions.
 */
public class Lobby extends ProtectedArea implements Updatable {
    /**
     * Creates new lobby object with specified name and region.
     * 
     * @param name
     *            name of lobby
     * @param region
     *            region of lobby
     */
    public Lobby(final String name, final CuboidRegion region) {
        super(name, region);
        this.setGlobalFlag(AreaFlag.BLOCK_BREAK, false);
        this.setGlobalFlag(AreaFlag.BLOCK_PLACE, false);
        this.setGlobalFlag(AreaFlag.PLAYER_GETDAMAGE, false);
        //Wierd call, isn't it?
        this.updateStart();
    }
    
    /**
     * Location of lobby spawn.
     */
    private Location lobbySpawn;
    private int      taskId        = 0;
    /**
     * How often should lobby check for players.
     */
    private long     checkInterval = 20; //40 ticks = 2 second.
    /**
     * The minimal Y coordinate value, after the lobby will teleport players to its spawn.
     */
    private int      thresholdY    = 50;
    
    /**
     * Returns lobby spawn.
     * 
     * @return spawn
     */
    public Location getSpawn() {
        return this.lobbySpawn;
    }
    
    /**
     * Sets lobby spawn.
     * 
     * @param location
     */
    public void setSpawn(final Location location) {
        this.lobbySpawn = location;
    }
    
    /**
     * Updates players. Adds potion effects and teleports them if needed.
     */
    private void updatePlayers() {
        for (Player player : this.getRegion().getPlayersXZ()) {
            //Lobby potion enhantsments.
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 30, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 30, 1));
            
            //In-void lobby teleport.
            if (player.getLocation().getY() < this.thresholdY)
                player.teleport(this.lobbySpawn);
        }
    }
    
    @Override
    public void updateStart() {
        UpdatedParts.registerPart(this);
        this.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Lobby.this.updatePlayers();
            }
        }, 0, this.checkInterval);
    }
    
    @Override
    public void updateStop() {
        Pexel.getScheduler().cancelTask(this.taskId);
    }
    
    /**
     * @return the thresholdY
     */
    public int getThresholdY() {
        return this.thresholdY;
    }
    
    /**
     * @return the checkInterval
     */
    public long getCheckInterval() {
        return this.checkInterval;
    }
    
    /**
     * @param checkInterval
     *            the checkInterval to set
     */
    public void setCheckInterval(final long checkInterval) {
        this.checkInterval = checkInterval;
    }
    
    /**
     * @param thresholdY
     *            the thresholdY to set
     */
    public void setThresholdY(final int thresholdY) {
        this.thresholdY = thresholdY;
    }
}
