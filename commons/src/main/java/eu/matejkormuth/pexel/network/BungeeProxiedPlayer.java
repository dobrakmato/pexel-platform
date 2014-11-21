package eu.matejkormuth.pexel.network;

/**
 * Bungee implementation of proxied player.
 */
public class BungeeProxiedPlayer extends ProxiedPlayer {
    // Internal
    net.md_5.bungee.api.connection.ProxiedPlayer internal;
    
    public BungeeProxiedPlayer(final net.md_5.bungee.api.connection.ProxiedPlayer player) {
        this.internal = player;
        // Didn't need more.
        this.uuid = this.internal.getUniqueId();
    }
    
    @Override
    public String getDisplayName() {
        return this.internal.getDisplayName();
    }
    
}
