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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Represents user role in Pexel.
 * 
 * @see Roles
 */
public class Role implements Permissiable {
    private final String          roleName;
    private final Set<Permission> permissions;
    
    public Role(final String name) {
        this.roleName = name;
        this.permissions = new HashSet<Permission>();
        Roles.mapping.put(name, this);
    }
    
    public Role(final String name, final Permission... permission) {
        this.roleName = name;
        this.permissions = new HashSet<Permission>(Arrays.asList(permission));
        Roles.mapping.put(name, this);
    }
    
    public Role(final String name, final Role parent, final Permission... permission) {
        this.roleName = name;
        this.permissions = new HashSet<Permission>(Arrays.asList(permission));
        this.permissions.addAll(parent.getPermissions());
        Roles.mapping.put(name, this);
    }
    
    /**
     * Returns display name of this {@link Role}.
     * 
     * @return display name
     */
    public String getDisplayName() {
        return this.roleName;
    }
    
    /**
     * Returns collection of all players that have this role.
     * 
     * @return collection of this role players
     */
    public Collection<Player> getAllPlayers() {
        return null;
    }
    
    @Override
    public boolean hasPermission(final Permission permission) {
        return this.permissions.contains(permission);
    }
    
    @Override
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(this.permissions);
    }
    
    @Override
    public void addPermission(final Permission permission) {
        this.permissions.add(permission);
    }
    
    @Override
    public void removePermission(final Permission permission) {
        this.permissions.remove(permission);
    }
}
