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

/**
 * Reasons for disconnecting from arena.
 */
public enum DisconnectReason {
    /**
     * Player has invoked disconnect from server.
     */
    PLAYER_DISCONNECT,
    /**
     * Player has lost connection to server.
     */
    PLAYER_CONNECTION_LOST,
    /**
     * Player has used /leave command or left by his decision.
     */
    PLAYER_LEAVE,
    /**
     * Invoked by game (eg. player has lost match).
     */
    LEAVE_BY_GAME,
    /**
     * Invoked by admin (eg. kicked for violating rules).
     */
    KICK_BY_SERVER,
    /**
     * Unknown or other reason. (Avoid using this one)
     */
    UNKNOWN,
}
