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
package eu.matejkormuth.pexel.master.cache;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.gson.JsonObject;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.master.cache.loaders.DatabaseProfileEntityLoader;
import eu.matejkormuth.pexel.master.db.ProfileEntity;

/**
 * Object that holds all caches on master.
 */
public class Caches {
    private final LoadingCache<UUID, ProfileEntity> profiles;
    
    public Caches() {
        this.profiles = CacheBuilder.newBuilder()
                .concurrencyLevel(4)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(2000)
                .build(new DatabaseProfileEntityLoader());
    }
    
    public LoadingCache<UUID, ProfileEntity> getProfileCache() {
        return this.profiles;
    }
    
    public long totalSize() {
        return this.profiles.size();
    }
    
    public long totalMemSize() {
        return 0;
    }
    
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("totalSize", this.totalSize());
        obj.addProperty("totalMemSize", this.totalMemSize());
        // Build array of caches.
        JsonObject caches = new JsonObject();
        caches.add("profile", Providers.JSON.toJsonTree(this.profiles));
        obj.add("caches", caches);
        // Return final object.
        return obj;
    }
}
