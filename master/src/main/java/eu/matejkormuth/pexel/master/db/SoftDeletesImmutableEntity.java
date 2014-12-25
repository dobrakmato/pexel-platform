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

import javax.persistence.Column;

import com.avaje.ebean.Ebean;

/**
 * Abstract class for easier implementation of delete and soft-delete on entities.
 */
public abstract class SoftDeletesImmutableEntity extends ImmutableEntity {
    @Column(name = "deletedAt")
    private long deletedAt;
    
    /**
     * Soft-deletes this object from database.
     */
    @Override
    public void delete() {
        this.deletedAt = System.currentTimeMillis();
        Ebean.save(this);
    }
    
    /**
     * Soft-deletes or hard deletes this object from database.
     */
    public void delete(final boolean hardDelete) {
        if (hardDelete) {
            Ebean.delete(this);
        }
        else {
            this.delete();
        }
    }
    
    /**
     * Returns timestamp of when was this entity soft-deleted.
     * 
     * @return unix timestamp of soft-delete of this entity
     */
    public long getDeletedAt() {
        return this.deletedAt;
    }
    
    /**
     * Checks if this entity is soft-deleted or not.
     * 
     * @return true if this entity is soft deleted
     */
    public boolean isDeleted() {
        return this.deletedAt == 0;
    }
}
