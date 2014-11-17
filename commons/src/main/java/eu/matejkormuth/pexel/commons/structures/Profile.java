package eu.matejkormuth.pexel.commons.structures;

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
