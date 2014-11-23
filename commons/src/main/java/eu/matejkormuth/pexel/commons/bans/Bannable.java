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
package eu.matejkormuth.pexel.commons.bans;

import eu.matejkormuth.pexel.commons.minigame.Minigame;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * Represents part of network, from which can be player banned.
 */
public interface Bannable {
    // Bannable ID prefixes.
    /**
     * Prefix for {@link ServerInfo}.
     */
    public static final String PREFIX_SERVER   = "server_";
    /**
     * Prefix for {@link Minigame}.
     */
    public static final String PREFIX_MINIGAME = "minigame_";
    
    /**
     * Name of the bannable part.
     * 
     * @return name of part.
     */
    public String getBannableName();
    
    /**
     * Returns ID of bannable part.
     * 
     * @return id of part.
     */
    public String getBannableID();
}
