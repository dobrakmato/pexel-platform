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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

/**
 * Section of configuration.
 */
@XmlType(name = "section")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationSection extends Unmarshaller.Listener {
    @XmlList
    protected List<ConfigurationEntry>                  entry;
    // Internal map.
    protected transient Map<String, ConfigurationEntry> map;
    
    /**
     * Returns value by specified key or null if not found.
     * 
     * @param key
     *            key
     * @return value
     */
    public ConfigurationEntry get(final String key) {
        return this.map.get(key);
    }
    
    /**
     * Returns value by key or returns default value. This also adds default value to config.
     * 
     * @param key
     *            key
     * @param defaultValue
     *            default values that will be used, when no value is found
     * @return value
     */
    public ConfigurationEntry get(final String key, final Object defaultValue) {
        if (this.map.containsKey(key)) { return this.get(key); }
        return this.add(new ConfigurationEntry(key, defaultValue));
    }
    
    public ConfigurationEntry add(final ConfigurationEntry entry) {
        this.entry.add(entry);
        this.map.put(entry.key, entry);
        return entry;
    }
    
    public void remove(final String key) {
        for (Iterator<ConfigurationEntry> iterator = this.entry.iterator(); iterator.hasNext();) {
            ConfigurationEntry ce = iterator.next();
            if (ce.key.equals(key)) {
                iterator.remove();
                this.map.remove(key);
            }
        }
    }
    
    @Override
    public void afterUnmarshal(final Object target, final Object parent) {
        super.afterUnmarshal(target, parent);
        // Create map.
        this.map = new HashMap<String, ConfigurationEntry>(this.entry.size());
        for (ConfigurationEntry entry : this.entry) {
            this.map.put(entry.key, entry);
        }
    }
}
