package eu.matejkormuth.pexel.master.responders;

import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.Proxy;
import eu.matejkormuth.pexel.protocol.requests.PlayerTeleportRequest;

/**
 * Responder for teleportation between servers.
 */
public class TeleportationResponder {
    
    public void onPlayerTeleport(final PlayerTeleportRequest request) {
        Proxy proxy = PexelMaster.getInstance().getProxy();
        ProxiedPlayer player = proxy.getPlayer(request.uuid);
        proxy.connect(player,
                PexelMaster.getInstance().getMasterServer().getSlave(request.targetServer));
    }
}
