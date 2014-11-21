package eu.matejkormuth.pexel.network;

import java.util.UUID;

/**
 * Class that represents player connected via proxy.
 */
public abstract class ProxiedPlayer {
    protected UUID uuid;
    
    public UUID getUniqueId() {
        return this.uuid;
    }
    
    public abstract String getDisplayName();
}
