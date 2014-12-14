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
package eu.matejkormuth.pexel.slave;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.arenas.AreaPermissions;
import eu.matejkormuth.pexel.commons.arenas.ProtectedArea;

/**
 * Class that represents lobby.
 */
public class Lobby extends ProtectedArea {
    /**
     * The lowest value of y co-ordinate players can be at before teleportation back to lobby spawn.
     */
    public static final int THRESHOLD_Y = 40;
    
    /**
     * Spawn location of this lobby.
     */
    private Location        spawn;
    
    public Lobby() {
        this.setGlobalPermission(AreaPermissions.BLOCK_BREAK, false);
        this.setGlobalPermission(AreaPermissions.BLOCK_PLACE, false);
        
        this.setGlobalPermission(AreaPermissions.PLAYER_DODAMAGE, false);
        this.setGlobalPermission(AreaPermissions.PLAYER_GETDAMAGE, false);
        
        this.setGlobalPermission(AreaPermissions.PLAYER_DROPITEM, false);
    }
    
    public void teleportPlayers() {
        //for (Player p : this.getRegion().getPlayersXZ()) {
        //    if (p.getLocation().getY() < Lobby.THRESHOLD_Y) {
        //        p.teleport(this.spawn);
        //    }
        //}
    }
    
    public Location getSpawn() {
        return this.spawn;
    }
    
    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }
}
