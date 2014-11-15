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

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Basic Ban implementation.
 */
@XmlType(name = "playerBan")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerBan implements Ban {
    // Lenght of ban in miliseconds, -1 means permanent.
    protected long      length = -1;
    /**
     * Reason of ban.
     */
    protected String    reason;
    protected BanAuthor author;
    protected long      creationTime;
    protected UUID      uuid;
    // Just for caching name.
    protected String    playerName;
    protected Bannable  bannable;
    
    /**
     * Creates PERMANENT ban.
     * 
     * @param reason
     *            reason of ban
     * @param author
     *            author of ban
     * @param player
     *            player
     * @param bannable
     *            banned part
     */
    public PlayerBan(final String reason, final BanAuthor author, final Player player,
            final Bannable bannable) {
        this(-1, reason, author, System.currentTimeMillis(), player.getUniqueId(),
                player.getName(), bannable);
    }
    
    /**
     * Creates TEMPORARY ban.
     * 
     * @param length
     *            length of ban in miliseconds.
     */
    public PlayerBan(final long length, final String reason, final BanAuthor author,
            final Player player, final Bannable bannable) {
        this(length, reason, author, System.currentTimeMillis(), player.getUniqueId(),
                player.getName(), bannable);
    }
    
    public PlayerBan(final long length, final String reason, final BanAuthor author,
            final long creationTime, final UUID uuid, final String playerName,
            final Bannable bannable) {
        this.length = length;
        this.reason = reason;
        this.author = author;
        this.creationTime = creationTime;
        this.uuid = uuid;
        this.playerName = playerName;
        this.bannable = bannable;
    }
    
    @Override
    public boolean isPermanent() {
        return this.length == -1;
    }
    
    @Override
    public BanAuthor getAuthor() {
        return this.author;
    }
    
    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
    
    @Override
    public Bannable getPart() {
        return this.bannable;
    }
    
    @Override
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public long getLength() {
        return this.length;
    }
    
    @Override
    public long getCreated() {
        return this.creationTime;
    }
    
    @Override
    public long getExpirationTime() {
        return this.creationTime + this.length;
    }
    
    @Override
    public String toString() {
        return "BanBase{creationTime:" + this.creationTime + ", length:" + this.length
                + ", reason:" + this.reason + ", author:" + this.author.getName()
                + ", BID:" + this.bannable.getBannableName() + ", BN:"
                + this.bannable.getBannableName() + ", uuid:" + this.uuid + "}";
    }
    
    public String getPlayerName() {
        return this.playerName;
    }
}
