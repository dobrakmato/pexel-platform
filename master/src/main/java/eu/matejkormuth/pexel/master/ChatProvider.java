package eu.matejkormuth.pexel.master;

import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.ProxyBrand;

/**
 * Provider or chat on master server.
 */
public class ChatProvider extends MasterComponent {
    public void sendMessage(final ProxiedPlayer player, final String message) {
        // Currently send only throught bungee.
        if (PexelMaster.getInstance().getProxy().getBrand() == ProxyBrand.BUNGEE_CORD) {
            PexelMaster.getInstance().getProxy().sendMessage(player, message);
        }
        else {
            // TODO: Fallback throught pexel-connection.
            
        }
    }
}
