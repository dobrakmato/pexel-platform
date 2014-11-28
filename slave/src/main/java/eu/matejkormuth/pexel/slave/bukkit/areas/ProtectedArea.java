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
package eu.matejkormuth.pexel.slave.bukkit.areas;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Optional;

import eu.matejkormuth.pexel.commons.CuboidRegion;

/**
 * Used for handling protected areas.
 * 
 * @author Mato Kormuth
 * 
 */
public class ProtectedArea {
    /**
     * Map of default values for flags.
     */
    public static final Map<AreaFlag, Boolean>            defaultFlags = new HashMap<AreaFlag, Boolean>();
    
    //Initialization of static values.
    static {
        ProtectedArea.defaultFlags.put(AreaFlag.BLOCK_BREAK, false);
        ProtectedArea.defaultFlags.put(AreaFlag.BLOCK_PLACE, false);
        ProtectedArea.defaultFlags.put(AreaFlag.PLAYER_GETDAMAGE, false);
        ProtectedArea.defaultFlags.put(AreaFlag.PLAYER_DODAMAGE, false);
        ProtectedArea.defaultFlags.put(AreaFlag.PLAYER_DROPITEM, false);
        ProtectedArea.defaultFlags.put(AreaFlag.PLAYER_STARVATION, false);
        ProtectedArea.defaultFlags.put(AreaFlag.AREA_CHAT_GOODBYE, true);
        ProtectedArea.defaultFlags.put(AreaFlag.AREA_CHAT_PERMISSIONDENIED, true);
        ProtectedArea.defaultFlags.put(AreaFlag.AREA_CHAT_WELCOME, true);
    }
    
    /**
     * Area region
     */
    protected CuboidRegion                                region;
    /**
     * Global area flags.
     */
    protected final Map<AreaFlag, Boolean>                globalFlags  = new HashMap<AreaFlag, Boolean>();
    /**
     * Player area flags.
     */
    protected final Map<UUID, HashMap<AreaFlag, Boolean>> playerFlags  = new HashMap<UUID, HashMap<AreaFlag, Boolean>>();
    /**
     * Owner of area.
     */
    protected AreaOwner                                   owner;
    /**
     * Name of area.
     */
    protected final String                                areaName;
    
    /**
     * Creates new area with specified name.
     * 
     * @param name
     */
    public ProtectedArea(final String name, final CuboidRegion region) {
        this.areaName = name;
        this.region = region;
    }
    
    /**
     * Returns value of global flag. If not specified uses parent's flag (default).
     * 
     * @param flag
     */
    public boolean getGlobalFlag(final AreaFlag flag) {
        if (this.globalFlags.get(flag) == null)
            if (ProtectedArea.defaultFlags.get(flag) == null)
                return false;
            else
                return ProtectedArea.defaultFlags.get(flag);
        else
            return this.globalFlags.get(flag);
        
    }
    
    /**
     * Returns value of player flag. If not specified uses parent's flag (global).
     * 
     * @param flag
     * @param player
     */
    public boolean getPlayerFlag(final AreaFlag flag, final UUID player) {
        if (this.playerFlags.containsKey(player))
            if (this.playerFlags.get(player).get(flag) == null)
                if (this.globalFlags.get(flag) == null)
                    if (ProtectedArea.defaultFlags.get(flag) == null)
                        return false;
                    else
                        return ProtectedArea.defaultFlags.get(flag);
                else
                    return this.globalFlags.get(flag);
            else
                return this.playerFlags.get(player).get(flag);
        else {
            if (this.globalFlags.get(flag) == null)
                if (ProtectedArea.defaultFlags.get(flag) == null)
                    return false;
                else
                    return ProtectedArea.defaultFlags.get(flag);
            else
                return this.globalFlags.get(flag);
        }
    }
    
    /**
     * Sets global flag.
     * 
     * @param flag
     *            flag to set
     * @param value
     *            value to set
     */
    public void setGlobalFlag(final AreaFlag flag, final boolean value) {
        this.globalFlags.put(flag, value);
    }
    
    /**
     * Sets player flag.
     * 
     * @param flag
     * @param value
     * @param player
     */
    public void setPlayerFlag(final AreaFlag flag, final boolean value, final UUID player) {
        if (this.playerFlags.containsKey(player))
            this.playerFlags.get(player).put(flag, value);
        else {
            this.playerFlags.put(player, new HashMap<AreaFlag, Boolean>());
            this.playerFlags.get(player).put(flag, value);
        }
    }
    
    /**
     * Returns area region.
     * 
     * @return region
     */
    public CuboidRegion getRegion() {
        return this.region;
    }
    
    /**
     * Returns area name.
     * 
     * @return name of area
     */
    public String getName() {
        return this.areaName;
    }
    
    /**
     * Returns area owner.
     * 
     * @return owner of area
     */
    public Optional<AreaOwner> getOwner() {
        return Optional.of(this.owner);
    }
}
