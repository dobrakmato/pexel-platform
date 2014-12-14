package eu.matejkormuth.pexel.slave;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Class that handles errors by teleporting players to safe server.
 */
public class LimboHandler {
    
    public static void handle(final Player player) {
        player.sendMessage("Something broke! Taking you to limbo.");
    }
    
}
