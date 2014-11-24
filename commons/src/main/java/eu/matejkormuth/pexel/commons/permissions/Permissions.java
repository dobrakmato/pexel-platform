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

/**
 * All default permissions in Pexel.
 */
public abstract class Permissions {
    /**
     * Permission to give temporary ban to name.
     */
    public static final Permission BAN_TEMPORARY = new Permission("pexel.ban.temporary");
    /**
     * Permission to hive permanent ban to name.
     */
    public static final Permission BAN_PERMANENT = new Permission("pexel.ban.permanent");
    /**
     * Permission to unban any kind of ban.
     */
    public static final Permission BAN_PARDON    = new Permission("pexel.ban.pardon");
    /**
     * Permission to give IP (internet protocol) ban.
     */
    public static final Permission BAN_IP        = new Permission("pexel.ban.ip");
    /**
     * Permission to kick player from server.
     */
    public static final Permission KICK          = new Permission("pexel.ban.kick");
    /**
     * Permission to broadcast global message.
     */
    public static final Permission BROADCAST     = new Permission("pexel.broadcast");
}
