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
package eu.matejkormuth.pexel.commons.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import eu.matejkormuth.pexel.commons.MultiValueMap;

/**
 * Entry (name and value) in configuration.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationValue {
    /**
     * Key in configuration.
     */
    @XmlAttribute
    protected String key;
    /**
     * Value in configuration.
     */
    protected Object value;
    
    public ConfigurationValue() {
    }
    
    private ConfigurationValue(final Object value, final String key) {
        this.key = key;
        this.value = value;
    }
    
    public ConfigurationValue(final String key, final Object value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final MultiValueMap<?, ?> value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final byte value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final short value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final int value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final long value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final float value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final double value) {
        this(value, key);
    }
    
    public ConfigurationValue(final String key, final ConfigurationSection value) {
        this(value, key);
    }
    
    @SuppressWarnings("unchecked")
    private <T> T get() {
        return (T) this.value;
    }
    
    public Object value() {
        return this.value;
    }
    
    public byte asByte() {
        return this.get();
    }
    
    public short asShort() {
        return this.get();
    }
    
    public int asInteger() {
        return this.get();
    }
    
    public long asLong() {
        return this.get();
    }
    
    public float asFloat() {
        return this.get();
    }
    
    public double asDouble() {
        return this.get();
    }
    
    public String asString() {
        return this.get();
    }
    
    public MultiValueMap<?, ?> asMultiValueMap() {
        return this.get();
    }
    
    public Boolean asBoolean() {
        return this.get();
    }
    
    public ConfigurationSection asSection() {
        return this.get();
    }
}
