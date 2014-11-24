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
package eu.matejkormuth.pexel.master.db;

import java.util.Collection;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.commons.permissions.Permission;
import eu.matejkormuth.pexel.commons.permissions.Role;

/**
 * Entity that represnts {@link Player}'s {@link Profile} in {@link Database}.
 */
public class ProfileEntity implements Profile {
    
    @Override
    public long getId() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public UUID getUUID() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getLastKnownName() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getXP() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public int getCoins() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public int getPremiumCoins() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public boolean hasPermission(final Permission permission) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Collection<Permission> getPermissions() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Role getRole() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
