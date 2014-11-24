package eu.matejkormuth.pexel.commons.permissions;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Interface for {@link Permission} manager.
 */
public class PermissionsManager {
    
    public boolean hasPermission(final Player player, final Permission permission) {
        return false;
    }
    
    public boolean hasPermission(final Role role, final Permission permission) {
        return false;
    }
}
