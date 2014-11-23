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

/**
 * All flags avaiable for areas.
 * 
 * @author Mato Kormuth
 * 
 */
public enum AreaFlag {
    /**
     * Block break event.
     */
    BLOCK_BREAK,
    /**
     * Block place event.
     */
    BLOCK_PLACE,
    /**
     * Player doing damage.
     */
    PLAYER_DODAMAGE,
    /**
     * Player dropping item/s.
     */
    PLAYER_DROPITEM,
    /**
     * Player getting damage.
     */
    PLAYER_GETDAMAGE,
    /**
     * Starvation of player.
     */
    PLAYER_STARVATION,
    /**
     * Permission for receiving "Permission denied" message.
     */
    AREA_CHAT_PERMISSIONDENIED,
    /**
     * Permission for receiving area welcome message.
     */
    AREA_CHAT_WELCOME,
    /**
     * Permission for receiving arena goodbye message.
     */
    AREA_CHAT_GOODBYE
}
