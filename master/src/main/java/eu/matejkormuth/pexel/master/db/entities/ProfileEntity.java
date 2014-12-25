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
package eu.matejkormuth.pexel.master.db.entities;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.Ebean;
import com.google.common.collect.Sets;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.commons.permissions.Permission;
import eu.matejkormuth.pexel.commons.permissions.Role;
import eu.matejkormuth.pexel.commons.permissions.Roles;
import eu.matejkormuth.pexel.master.db.MutableEntity;
import eu.matejkormuth.pexel.master.db.Database;

/**
 * Entity that represnts {@link Player}'s {@link Profile} in {@link Database}.
 */
@Entity
@Table(name = "profiles")
public class ProfileEntity extends MutableEntity implements Profile {
    @Transient
    private final Set<Permission> userPermisisons;
    
    @Id
    private UUID                  uuid;
    private String                lastKnownName;
    @Column(name = "exp")
    private int                   xp;
    @Column(name = "coins")
    private int                   coins;
    @Column(name = "premium_coins")
    private int                   premiumCoins;
    @Column(name = "role", length = 16)
    private String                roleName;
    @Column(name = "locale", length = 2)
    private String                localeCode;
    
    public ProfileEntity() {
        this.userPermisisons = Sets.newHashSet();
    }
    
    @Override
    public long getId() {
        return 0;
    }
    
    @Override
    public UUID getUUID() {
        return this.uuid;
    }
    
    @Override
    public String getLastKnownName() {
        return this.lastKnownName;
    }
    
    @Override
    public int getXP() {
        return this.xp;
    }
    
    @Override
    public int getCoins() {
        return this.coins;
    }
    
    @Override
    public int getPremiumCoins() {
        return this.premiumCoins;
    }
    
    @Override
    public boolean hasPermission(final Permission permission) {
        if (this.userPermisisons.contains(permission))
            return true;
        return this.getRole().hasPermission(permission);
    }
    
    @Override
    public Set<Permission> getPermissions() {
        return Sets.union(this.userPermisisons, this.getRole().getPermissions());
    }
    
    @Override
    public Role getRole() {
        return Roles.byName(this.roleName);
    }
    
    @Override
    public void addPermission(final Permission permission) {
        this.userPermisisons.add(permission);
    }
    
    @Override
    public void removePermission(final Permission permission) {
        this.userPermisisons.remove(permission);
    }
    
    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }
    
    public void save() {
        Ebean.save(this);
    }
}
