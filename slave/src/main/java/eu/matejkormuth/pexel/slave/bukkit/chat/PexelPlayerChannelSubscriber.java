package eu.matejkormuth.pexel.slave.bukkit.chat;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Channel subscriber implementation for pexel {@link Player} object.
 */
public class PexelPlayerChannelSubscriber implements ChannelSubscriber {
    private final SubscribeMode mode;
    private final Player        player;
    
    public PexelPlayerChannelSubscriber(final Player player, final SubscribeMode mode) {
        this.mode = mode;
        this.player = player;
    }
    
    @Override
    public void sendMessage(final String message) {
        this.player.sendMessage(message);
    }
    
    @Override
    public SubscribeMode getMode() {
        return this.mode;
    }
    
    @Override
    public boolean isOnline() {
        return this.player.isOnline();
    }
    
    @Override
    public String getName() {
        return this.player.getName();
    }
    
}
