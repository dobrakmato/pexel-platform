package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Configuration value abstract class.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public abstract class ConfigurationValue<T> {
    /**
     * Key in configuration.
     */
    protected String key;
    
    // JAXB needs this.
    public ConfigurationValue() {
    }
    
    // Needed contructor.
    public ConfigurationValue(final String key, final T value) {
        this.key = key;
        this.setValue(value);
    }
    
    // Getter and setter.
    public abstract void setValue(T value);
    
    @XmlAttribute
    public abstract T getValue();
    
    // Shortcuts.
    public T get() {
        return this.getValue();
    }
    
    public void set(final T value) {
        this.setValue(value);
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    @XmlAttribute
    public String getKey() {
        return this.key;
    }
}
