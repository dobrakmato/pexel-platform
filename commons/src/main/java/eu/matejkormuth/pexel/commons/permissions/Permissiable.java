package eu.matejkormuth.pexel.commons.permissions;

import java.util.Collection;

/**
 * Interface that represents type that can be permissiable.
 */
public interface Permissiable {
    
    /**
     * Returns whether this roles has permission to access specified permission.
     * 
     * @param permission
     *            permission to check
     * @return whether this role has this permission
     */
    public boolean hasPermission(Permission permission);
    
    /**
     * Returns collection of permissions that can this role access.
     * 
     * @return collection of this role permissions
     */
    public Collection<Permission> getPermissions();
}
