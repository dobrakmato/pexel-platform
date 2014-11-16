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
        if (this.internalMap.containsKey(key)) {
            return this.internalMap.get(key).get(index);
        }
        else {
            return null;
        }
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
        return this.internalMap.get(key).remove(value);
    }
    
    public List<V> removeAll(final K key) {
        return this.internalMap.remove(key);
    }
    
    public int size(final K key) {
        if (this.internalMap.get(key) != null) {
            return this.internalMap.get(key).size();
        }
        else {
            return 0;
        }
    }
    
    public int size() {
        return this.size();
    }
    
    public boolean isEmpty() {
        return this.internalMap.size() == 0;
    }
    
    public boolean isEmpty(final K key) {
        if (this.internalMap.containsKey(key)) {
            return this.internalMap.get(key).size() == 0;
        }
        else {
            return true;
        }
    }
    
    public boolean containsKey(final K key) {
        return this.internalMap.containsKey(key);
    }
    
    public boolean containsValue(final K key, final V value) {
        if (this.internalMap.containsKey(key)) {
            return this.internalMap.get(key).contains(value);
        }
        else {
            return false;
        }
    }
    
    public Set<K> keySet() {
        return this.keySet();
    }
}
