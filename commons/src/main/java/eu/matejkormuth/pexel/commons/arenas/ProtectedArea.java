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
package eu.matejkormuth.pexel.commons.arenas;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.CuboidRegion;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.permissions.Permission;

/**
 * Area that is protected.
 */
public abstract class ProtectedArea {
    private String                               tag;
    private final CuboidRegion                   region;
    
    /**
     * Map of default values for flags.
     */
    public static final Map<Permission, Boolean> defaultFlags = new HashMap<Permission, Boolean>();
    
    //Initialization of static values.
    static {
        ProtectedArea.defaultFlags.put(AreaPermissions.BLOCK_BREAK, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.BLOCK_PLACE, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.PLAYER_GETDAMAGE, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.PLAYER_DODAMAGE, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.PLAYER_DROPITEM, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.PLAYER_STARVATION, false);
        ProtectedArea.defaultFlags.put(AreaPermissions.AREA_CHAT_GOODBYE, true);
        ProtectedArea.defaultFlags.put(AreaPermissions.AREA_CHAT_PERMISSIONDENIED, true);
        ProtectedArea.defaultFlags.put(AreaPermissions.AREA_CHAT_WELCOME, true);
    }
    
    public ProtectedArea(final CuboidRegion region) {
        this.region = region;
    }
    
    public ProtectedArea(final CuboidRegion region, final String tag) {
        this.region = region;
        this.tag = tag;
    }
    
    /**
     * Global area flags.
     */
    protected final Map<Permission, Boolean>                globalFlags = new HashMap<Permission, Boolean>();
    /**
     * Player area flags.
     */
    protected final Map<UUID, HashMap<Permission, Boolean>> playerFlags = new HashMap<UUID, HashMap<Permission, Boolean>>();
    
    /**
     * Sets global permission value.
     * 
     * @param permission
     *            permission to set
     * @param value
     *            true of permission should be allowed, false otherwise
     */
    public void setGlobalPermission(final Permission permission, final boolean value) {
        this.globalFlags.put(permission, value);
    }
    
    /**
     * Returns value of global flag. If not specified uses parent's flag (default).
     * 
     * @param flag
     */
    public boolean getGlobalPermission(final Permission permission) {
        if (this.globalFlags.get(permission) == null)
            if (ProtectedArea.defaultFlags.get(permission) == null)
                return false;
            else
                return ProtectedArea.defaultFlags.get(permission);
        else
            return this.globalFlags.get(permission);
        
    }
    
    /**
     * Sets player permission.
     * 
     * @param flag
     * @param value
     * @param player
     */
    public void setPlayerPermission(final Permission permission, final boolean value,
            final Player player) {
        if (this.playerFlags.containsKey(player.getUniqueId()))
            this.playerFlags.get(player.getUniqueId()).put(permission, value);
        else {
            this.playerFlags.put(player.getUniqueId(),
                    new HashMap<Permission, Boolean>());
            this.playerFlags.get(player.getUniqueId()).put(permission, value);
        }
    }
    
    /**
     * Returns whether thisplayer has specified Permission in this area.
     * 
     * @see AreaPermissions
     * @param permission
     *            permission to check
     * @param player
     *            player to check
     * @return true if player has permission, else otherwise
     */
    public boolean hasPermission(final Permission permission, final Player player) {
        if (this.playerFlags.containsKey(player.getUniqueId()))
            if (this.playerFlags.get(player).get(permission) == null)
                if (this.globalFlags.get(permission) == null)
                    if (ProtectedArea.defaultFlags.get(permission) == null)
                        return false;
                    else
                        return ProtectedArea.defaultFlags.get(permission);
                else
                    return this.globalFlags.get(permission);
            else
                return this.playerFlags.get(player).get(permission);
        else {
            if (this.globalFlags.get(permission) == null)
                if (ProtectedArea.defaultFlags.get(permission) == null)
                    return false;
                else
                    return ProtectedArea.defaultFlags.get(permission);
            else
                return this.globalFlags.get(permission);
        }
    }
    
    /**
     * Returns region of this protected area.
     * 
     * @return protected region
     */
    public CuboidRegion getRegion() {
        return this.region;
    }
    
    /**
     * @return the tag
     */
    public String getTag() {
        return this.tag;
    }
    
    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(final String tag) {
        this.tag = tag;
    }
}
