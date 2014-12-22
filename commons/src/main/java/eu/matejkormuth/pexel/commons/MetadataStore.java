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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.common.reflect.TypeToken;

/**
 * Class that holds metadata.
 */
public class MetadataStore implements Metadatable {
    private static Type               MAP_TYPE = new TypeToken<Map<String, String>>() {
                                                   private static final long serialVersionUID = 7820627094834822190L;
                                               }.getType();
    // Internal map containing metadata.                                           
    private final Map<String, String> internal;
    
    private MetadataStore() {
        this(new HashMap<String, String>(10));
    }
    
    private MetadataStore(final Map<String, String> metadata) {
        this.internal = metadata;
    }
    
    public MetadataStore(final String json) {
        this.internal = Providers.JSON.fromJson(json, MetadataStore.MAP_TYPE);
    }
    
    public static MetadataStore create() {
        return new MetadataStore();
    }
    
    public static MetadataStore create(final String key, final String value) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return new MetadataStore(map);
    }
    
    public static MetadataStore create(final Map<String, String> metadata) {
        return new MetadataStore(metadata);
    }
    
    /**
     * Converts metadata to json string.
     * 
     * @return json encoded metadata
     */
    public String toJSON() {
        return Providers.JSON.toJson(this.internal);
    }
    
    @Override
    public void setMetadata(final String key, final String value) {
        this.internal.put(key, value);
    }
    
    @Override
    public String getMetadata(final String key) {
        return this.internal.get(key);
    }
    
}
