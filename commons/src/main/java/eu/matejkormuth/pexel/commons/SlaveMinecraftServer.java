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
package eu.matejkormuth.pexel.commons;


/**
 * Interface that specifies all actions that slave minecraft software should do.
 */
public interface SlaveMinecraftServer {
    
    /**
     * Returns minecraft server's version.
     * 
     * @return version of server that slave runs on
     */
    public String getVersion();
    
    /**
     * Returns maximum amount of players that can be online at this server at the same time.
     * 
     * @return number of slots
     */
    public int getSlots();
    
    /**
     * Returns {@link SlaveMinecraftServerType} of this {@link SlaveMinecraftServer}.
     * 
     * @return
     */
    public SlaveMinecraftServerType getType();
}
