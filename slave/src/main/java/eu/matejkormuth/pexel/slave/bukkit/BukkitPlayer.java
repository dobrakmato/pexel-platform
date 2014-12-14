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
package eu.matejkormuth.pexel.slave.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.GameMode;
import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.MetadataStore;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.slave.PexelSlave;

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
        PexelSlave.getInstance()
                .getComponent(BukkitTeleporter.class)
                .teleport(this, loc);
    }
    
    @Override
    public MetadataStore getMetadata() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public UUID getUniqueId() {
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
    
    @Override
    public String getName() {
        return this.internal.getName();
    }
    
    @Override
    public int getEntityId() {
        return this.internal.getEntityId();
    }
    
    @Override
    public double getHealth() {
        return this.internal.getHealth();
    }
    
    @Override
    public Location getLocation() {
        return new Location(this.internal.getLocation().getX(),
                this.internal.getLocation().getY(), this.internal.getLocation().getZ(),
                this.internal.getLocation().getYaw(), this.internal.getLocation()
                        .getPitch(), this.internal.getLocation().getWorld().getUID());
    }
    
    @Override
    public void sendMessage(final String msg) {
        this.internal.sendMessage(msg);
    }
    
    @Override
    public void playSound(final Location location, final Sound sound,
            final float volume, final float pitch) {
        org.bukkit.Location loc = new org.bukkit.Location(
                Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch());
        this.internal.playSound(loc, sound, volume, pitch);
    }
    
    @Override
    public Inventory getInventory() {
        return this.internal.getInventory();
    }
    
    @Override
    public void setGameMode(final GameMode mode) {
        this.internal.setGameMode(org.bukkit.GameMode.valueOf(mode.name()));
    }
    
    @Override
    public void addPotionEffect(final PotionEffect potionEffect) {
        this.internal.addPotionEffect(potionEffect);
    }
    
    @Override
    public void setAllowFlight(final boolean b) {
        this.internal.setAllowFlight(b);
    }
    
    @Override
    public void setFlying(final boolean b) {
        this.internal.setFlying(b);
    }
    
    @Override
    public void removePotionEffect(final PotionEffectType nightVision) {
        this.internal.removePotionEffect(nightVision);
    }
    
    @Override
    public void setFoodLevel(final int i) {
        this.internal.setFoodLevel(i);
    }
    
    @Override
    public boolean isOnline() {
        return this.internal.isOnline();
    }
    
    @Override
    public void openInventory(final Inventory inventory) {
        this.internal.openInventory(inventory);
    }
    
    @Override
    public void performCommand(final String command) {
        this.internal.performCommand(command);
    }
    
    @Override
    public boolean isOp() {
        return this.internal.isOp();
    }
    
    @Override
    public void setBossBar(final String text, final float percent) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setHealth(final double level) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setPlayerTime(final long l, final boolean b) {
        this.internal.setPlayerTime(l, b);
    }
}
