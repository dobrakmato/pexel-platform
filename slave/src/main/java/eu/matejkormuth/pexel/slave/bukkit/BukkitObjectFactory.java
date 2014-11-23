package eu.matejkormuth.pexel.slave.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

import eu.matejkormuth.pexel.commons.AbstractObjectFactory;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.slave.BukkitPlayer;

/**
 * Bukkit implementation of {@link AbstractObjectFactory}.
 */
public class BukkitObjectFactory extends AbstractObjectFactory {
    @Override
    public Player getPlayer(final UUID uuid) {
        return new BukkitPlayer(Bukkit.getPlayer(uuid));
    }
    
    @Override
    public Player getPlayer(final Object platformType) {
        if (platformType instanceof org.bukkit.entity.Player) {
            return new BukkitPlayer((org.bukkit.entity.Player) platformType);
        }
        else {
            throw new IllegalArgumentException(
                    "platformType must be type of org.bukkit.entity.Player");
        }
    }
}
