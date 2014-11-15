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

import org.bukkit.entity.Player;

/**
 * Interface that specified ban.
 */
public interface Ban {
    /**
     * Returns whether is ban permanent or not.
     * 
     * @return true if ban is permanent, false otherwise.
     */
    public boolean isPermanent();
    
    /**
     * Returns author of ban.
     * 
     * @return author of ban.
     */
    public BanAuthor getAuthor();
    
    /**
     * Return banned player.
     * 
     * @return banned player.
     */
    public Player getPlayer();
    
    /**
     * Returns part of network, from which is player banned.
     * 
     * @return part of network.
     */
    public Bannable getPart();
    
    /**
     * Returns reason of this ban.
     * 
     * @return reson of ban.
     */
    public String getReason();
    
    /**
     * Returns length of ban in miliseconds if is ban temporary, -1 if is ban permanent.
     * 
     * @return lenght in ms or -1.
     */
    public long getLength();
    
    /**
     * Returns creation time of ban in epoch time format or -1, if is ban permanent.
     * 
     * @return timestamp of time at creation.
     */
    public long getCreated();
    
    /**
     * Returns time, when ban expiries or -1, if is ban permanent.
     * 
     * @return epoch timestamp
     */
    public long getExpirationTime();
}
