// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.commons.permissions;

import java.util.Collection;

/**
 * Interface that represents type that can be permissiable.
 */
public interface Permissiable {
    /**
     * Adds {@link Permission} to this object.
     * 
     * @param permission
     *            permission to add
     */
    public void addPermission(Permission permission);
    
    /**
     * Removes {@link Permission} from this object.
     * 
     * @param permission
     *            permission to remove
     */
    public void removePermission(Permission permission);
    
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
