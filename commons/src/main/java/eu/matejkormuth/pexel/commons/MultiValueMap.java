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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.math.RandomUtils;

@XmlRootElement
public class MultiValueMap<K, V> implements Serializable {
    private static final long     serialVersionUID = -2507916622202922554L;
    private final Map<K, List<V>> internalMap;
    
    public MultiValueMap() {
        this.internalMap = new HashMap<K, List<V>>();
    }
    
    public void add(final K key, final V value) {
        if (this.internalMap.containsKey(key)) {
            this.internalMap.get(key).add(value);
        }
        else {
            ArrayList<V> list = new ArrayList<V>();
            list.add(value);
            this.internalMap.put(key, list);
        }
    }
    
    public void addAll(final K key, final Collection<V> values) {
        if (this.internalMap.containsKey(key)) {
            this.internalMap.get(key).addAll(values);
        }
        else {
            this.internalMap.put(key, new ArrayList<V>(values));
        }
    }
    
    public V get(final K key, final int index) {
        if (this.internalMap.containsKey(key)) { return this.internalMap.get(key).get(
                index); }
        return null;
    }
    
    public Collection<V> getAll(final K key) {
        return Collections.unmodifiableCollection(this.internalMap.get(key));
    }
    
    public V getRandom(final K key) {
        return this.get(key, RandomUtils.nextInt(this.size(key)));
    }
    
    public void clear(final K key) {
        this.internalMap.get(key).clear();
    }
    
    public void clear() {
        this.internalMap.clear();
    }
    
    public boolean remove(final K key, final V value) {
        if (this.internalMap.containsKey(key)) { return this.internalMap.get(key)
                .remove(value); }
        return false;
    }
    
    public List<V> removeAll(final K key) {
        return this.internalMap.remove(key);
    }
    
    public int size(final K key) {
        if (this.internalMap.get(key) != null) { return this.internalMap.get(key).size(); }
        return 0;
    }
    
    public int size() {
        return this.size();
    }
    
    public boolean isEmpty() {
        return this.internalMap.size() == 0;
    }
    
    public boolean isEmpty(final K key) {
        if (this.internalMap.containsKey(key)) { return this.internalMap.get(key).size() == 0; }
        return true;
    }
    
    public boolean containsKey(final K key) {
        return this.internalMap.containsKey(key);
    }
    
    public boolean containsValue(final K key, final V value) {
        if (this.internalMap.containsKey(key)) { return this.internalMap.get(key)
                .contains(value); }
        return false;
    }
    
    public Set<K> keySet() {
        return this.keySet();
    }
}
