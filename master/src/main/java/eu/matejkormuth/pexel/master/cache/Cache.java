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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that represents cache.
 */
public abstract class Cache<K, V> implements CacheAccess<K, V>, CacheLoader<K, V> {
    private final Map<K, CacheEntry<V>> cache;
    protected long                      objectLifetime = 1000 * 60 * 30; //should be changed by testing. (current 30 minutes) 
    protected int                       objectMaxCount = 1024;
    
    public Cache() {
        this.cache = new ConcurrentHashMap<K, CacheEntry<V>>(this.objectMaxCount);
    }
    
    @Override
    public void remove(final K key) {
        this.cache.remove(key);
    }
    
    @Override
    public boolean has(final K key) {
        if (key == null) { throw new IllegalArgumentException("key"); }
        
        return this.cache.containsKey(key);
    }
    
    public void put(final K key, final V value) {
        long expireBy = System.currentTimeMillis() + this.objectLifetime;
        this.cache.put(key, new CacheEntry<V>(expireBy, value));
    }
    
    @Override
    public V get(final K key) {
        if (key == null) { throw new IllegalArgumentException("key"); }
        
        if (this.cache.containsKey(key)) {
            if (this.cache.get(key).expireBy < System.currentTimeMillis()) {
                return this.cache.remove(key).getEntry();
            }
            else {
                return this.cache.get(key).getEntry();
            }
        }
        else {
            this.load(key);
            return this.cache.get(key).getEntry();
        }
    }
    
    public int size() {
        return this.cache.size();
    }
    
    /**
     * Entry in cache.
     */
    public static class CacheEntry<V> {
        private final long expireBy;
        private final V    entry;
        
        public CacheEntry(final long expireBy, final V entry) {
            super();
            this.expireBy = expireBy;
            this.entry = entry;
        }
        
        public long getExpireBy() {
            return this.expireBy;
        }
        
        public V getEntry() {
            return this.entry;
        }
        
    }
}
