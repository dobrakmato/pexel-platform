package eu.matejkormuth.pexel.commons.actions;

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * {@link Action} that teleports player to specified location and/or server.
 */
public class TeleportAction implements Action {
    private final Location   location;
    private final ServerInfo server;
    
    /**
     * Constructs new {@link TeleportAction} that teleports player to specified {@link Location} at specified server.
     * Use {@link ServerInfo#localServer()} to obtain local {@link ServerInfo}.
     * 
     * @param server
     *            server to teleport player to
     * @param location
     *            target location on server
     */
    public TeleportAction(final ServerInfo server, final Location location) {
        this.server = server;
        this.location = location;
    }
    
    @Override
    public void execute(final Player player) {
        // If is player at target server.
        if (this.server == ServerInfo.localServer()) {
            // Just teleport him to location.
            player.teleport(this.location);
        }
        else {
            // TODO: Perform cross server teleport.
        }
    }
    
    @Override
    public void execute(final Collection<Player> players) {
        // If is player at target server.
        if (this.server == ServerInfo.localServer()) {
            for (Player player : players) {
                // Just teleport him to location.
                player.teleport(this.location);
            }
        }
        else {
            // TODO: Perform cross server teleport.
        }
    }
    
    public ServerInfo getServer() {
        return this.server;
    }
    
    public Location getLocation() {
        return this.location;
    }
}
