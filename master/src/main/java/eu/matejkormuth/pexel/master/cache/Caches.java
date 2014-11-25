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

import com.google.gson.JsonObject;

import eu.matejkormuth.pexel.commons.Providers;

/**
 * Object that holds all caches on master.
 */
public class Caches {
    private ProfileCache profileCache;
    
    public ProfileCache getProfileCache() {
        return this.profileCache;
    }
    
    public int totalSize() {
        return this.profileCache.size();
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
        caches.add("profile", Providers.JSON.toJsonTree(this.profileCache));
        obj.add("caches", caches);
        // Return final object.
        return obj;
    }
}
