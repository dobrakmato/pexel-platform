package eu.matejkormuth.pexel.commons.chat;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Channel subscriber implementation for pexel {@link Player} object.
 */
public class PlayerChannelSubscriber implements ChannelSubscriber {
    private final SubscribeMode mode;
    private final Player        player;
    
    public PlayerChannelSubscriber(final Player player, final SubscribeMode mode) {
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
    
    public Player getPlayer() {
        return this.player;
    }
    
}
