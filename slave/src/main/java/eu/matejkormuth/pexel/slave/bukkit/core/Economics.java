package eu.matejkormuth.pexel.slave.bukkit.core;

import org.bukkit.entity.Player;

/**
 *
 */
public class Economics {
    public void addCoins(final Player player, final int amount) {
        StorageEngine.getProfile(player.getUniqueId()).addPoints(amount);
    }
}
