package eu.matejkormuth.pexel.slave;

import java.util.UUID;

import eu.matejkormuth.pexel.commons.Commitable;
import eu.matejkormuth.pexel.commons.MetadataStore;
import eu.matejkormuth.pexel.commons.Metadatable;
import eu.matejkormuth.pexel.commons.structures.Profile;

/**
 * Class that represents profile of player and implementes {@link Profile} and {@link Commitable}.
 */
public class PlayerProfile implements Profile, Commitable, Metadatable {
    private long          id;
    private String        uuid;
    private String        lastKnownName;
    private int           xp;
    private int           coins;
    private int           premiumCoins;
    private MetadataStore metadata;
    
    @Override
    public long getId() {
        return this.id;
    }
    
    @Override
    public UUID getUUID() {
        return UUID.fromString(this.uuid);
    }
    
    @Override
    public String getLastKnownName() {
        return this.lastKnownName;
    }
    
    @Override
    public int getXP() {
        return this.xp;
    }
    
    @Override
    public int getCoins() {
        return this.coins;
    }
    
    @Override
    public int getPremiumCoins() {
        return this.premiumCoins;
    }
    
    @Override
    public void commit() {
        
    }
    
    @Override
    public void setMetadata(final String key, final String value) {
        if (this.metadata == null) {
            this.metadata = MetadataStore.create(key, value);
        }
    }
    
    @Override
    public String getMetadata(final String key) {
        if (this.metadata == null) {
            //TODO: this.requestMetadata();
        }
        return this.metadata.getMetadata(key);
    }
}
