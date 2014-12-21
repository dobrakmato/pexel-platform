package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import eu.matejkormuth.pexel.commons.configuration.ConfigurationSection;

/**
 * ConfigurationSection {@link ConfigurationValue}.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class SectionConfigurationValue extends ConfigurationValue<ConfigurationSection> {
    @XmlAttribute
    private final String         type = "section";
    @XmlAttribute
    private ConfigurationSection value;
    
    // JAXB needs this.
    public SectionConfigurationValue() {
    }
    
    public SectionConfigurationValue(final String key, final ConfigurationSection value) {
        super(key, value);
    }
    
    @Override
    public void setValue(final ConfigurationSection value) {
        this.value = value;
    }
    
    @Override
    public ConfigurationSection getValue() {
        return this.value;
    }
}
