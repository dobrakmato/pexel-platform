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
package eu.matejkormuth.pexel.master.db;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.matejkormuth.pexel.commons.bans.Ban;
import eu.matejkormuth.pexel.commons.bans.BanAuthor;
import eu.matejkormuth.pexel.commons.bans.Bannable;
import eu.matejkormuth.pexel.commons.bans.BannableIdImpl;
import eu.matejkormuth.pexel.commons.bans.PlayerBanAuthor;
import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.master.PexelMaster;

/**
 * {@link Ban} database entity.
 */
@Entity
@Table(name = "bans")
public class BanEntity implements Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long    id;
    @Column(name = "permanent")
    private boolean permanent;
    @Column(name = "reason", nullable = true, length = 255)
    private String  reason;
    @Column(name = "length")
    private long    length;
    @Column(name = "createdAt")
    private long    createdAt;
    @Column(name = "author")
    private UUID    author;
    @Column(name = "player")
    private UUID    player;
    @Column(name = "partId")
    private String  networkPartId;
    
    @Override
    public boolean isPermanent() {
        return this.permanent;
    }
    
    @Override
    public BanAuthor getAuthor() {
        return new PlayerBanAuthor(PexelMaster.getInstance()
                .getCaches()
                .getProfile(this.author));
    }
    
    @Override
    public Profile getPlayer() {
        return PexelMaster.getInstance().getCaches().getProfile(this.player);
    }
    
    @Override
    public Bannable getNetworkPart() {
        return new BannableIdImpl(this.networkPartId);
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
    public long getCreatedAt() {
        return this.createdAt;
    }
    
    @Override
    public long getExpireAt() {
        return this.createdAt + this.length;
    }
    
}
