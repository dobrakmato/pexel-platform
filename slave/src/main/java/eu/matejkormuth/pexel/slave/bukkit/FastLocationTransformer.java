package eu.matejkormuth.pexel.slave.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class FastLocationTransformer {
    public static final Location trans(
            final eu.matejkormuth.pexel.commons.Location location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(),
                location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }
    
    public static final eu.matejkormuth.pexel.commons.Location trans(final Location loc) {
        return new eu.matejkormuth.pexel.commons.Location(loc.getX(), loc.getY(),
                loc.getZ(), loc.getYaw(), loc.getPitch(), loc.getWorld().getUID());
    }
}
