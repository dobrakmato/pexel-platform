package eu.matejkormuth.pexel.commons;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Entry (name and value) in configuration.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationEntry {
    /**
     * Key in configuration.
     */
    @XmlAttribute
    protected String key;
    /**
     * Value in configuration.
     */
    protected Object value;
    
    public ConfigurationEntry() {
    }
    
    private ConfigurationEntry(final Object value, final String key) {
        this.key = key;
        this.value = value;
    }
    
    public ConfigurationEntry(final String key, final byte value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final short value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final int value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final long value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final float value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final double value) {
        this(value, key);
    }
    
    public ConfigurationEntry(final String key, final String value) {
        this((Object) value, key);
    }
    
    public ConfigurationEntry(final String key, final ConfigurationSection value) {
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
    
    public ConfigurationSection asSection() {
        return this.get();
    }
}
