package eu.matejkormuth.pexel.slave.events.player;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.slave.events.Event;

/**
 * Player event.
 */
public abstract class PlayerEvent extends Event {
    protected Player player;
    
    public PlayerEvent(final Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
