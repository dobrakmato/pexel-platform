package eu.matejkormuth.pexel.slave.sponge;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.MetadataStore;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.data.Profile;

/**
 * Sponge implementation of player.
 */
public class SpongePlayer extends Player {
    org.spongepowered.api.entity.player.Player internal;
    
    public SpongePlayer(final org.spongepowered.api.entity.player.Player player) {
        this.internal = player;
    }
    
    @Override
    public Profile getProfile() {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void teleport(final Location loc) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getEntityId() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public double getHealth() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void sendMessage(final String msg) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void playSound(final Location location, final Sound sound,
            final float volume, final float pitch) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Inventory getInventory() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setGameMode(final GameMode gameMode) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void addPotionEffect(final PotionEffect potionEffect) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setAllowFlight(final boolean allowFlight) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setFlying(final boolean flying) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void removePotionEffect(final PotionEffectType potionEffectType) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setFoodLevel(final int foodLevel) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isOnline() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean isOp() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void openInventory(final Inventory inventory) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void performCommand(final String command) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getDisplayName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
