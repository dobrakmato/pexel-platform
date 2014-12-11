package eu.matejkormuth.pexel.slave.sponge;

import java.util.UUID;

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
        return this.internal.getName();
    }
    
    @Override
    public int getEntityId() {
        return 0; //TODO
    }
    
    @Override
    public double getHealth() {
        return this.internal.getHealth();
    }
    
    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void sendMessage(final String msg) {
        this.internal.sendMessage(msg);
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
    public void addPotionEffect(final PotionEffect potionEffect) {
        // TODO Auto-generated method stub
        //this.internal.addPotionEffect(PotionEffectTypes.JUMP_BOOST, true);
    }
    
    @Override
    public void setAllowFlight(final boolean allowFlight) {
        this.internal.setAllowFlight(allowFlight);
    }
    
    @Override
    public void setFlying(final boolean flying) {
        //this.internal.setFly
    }
    
    @Override
    public void removePotionEffect(final PotionEffectType potionEffectType) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setFoodLevel(final int foodLevel) {
        //this.internal.setHunger(foodLevel);
    }
    
    @Override
    public boolean isOnline() {
        return this.internal.isOnline();
    }
    
    @Override
    public boolean isOp() {
        return false;//return this.internal.is
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
        return this.internal.getDisplayName();
    }
    
    @Override
    public void setBossBar(final String text, final float percent) {
        
    }
    
    @Override
    public void setHealth(final double level) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setGameMode(final eu.matejkormuth.pexel.commons.GameMode survival) {
        
    }
    
}
