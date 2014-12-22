package eu.matejkormuth.pexel.commons.actions;

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * <p>
 * Interface that represents action that can be executed on player.
 * <p>
 * 
 * <p>
 * For example {@link TeleportAction} caches {@link Location} and target server {@link ServerInfo} for teleport. You can
 * then run that action on set of players and each one will be teleported using that one {@link TeleportAction}.
 * </p>
 * 
 * @see TeleportAction
 * @see ArbitraryAction
 */
public interface Action {
    /**
     * Executes this action on specified player.
     * 
     * @param player
     *            player to execute this action at
     */
    void execute(Player player);
    
    /**
     * Executes this action on specified collection of players.
     * 
     * @param players
     *            collection of player to execute this action at
     */
    void execute(Collection<Player> players);
}
