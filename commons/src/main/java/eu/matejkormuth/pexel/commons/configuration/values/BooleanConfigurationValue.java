package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class BooleanConfigurationValue extends ConfigurationValue<Boolean> {
    @XmlAttribute
    private final String type = "boolean";
    @XmlAttribute
    private boolean      value;
    
    // JAXB needs this.
    public BooleanConfigurationValue() {
    }
    
    public BooleanConfigurationValue(final String key, final boolean value) {
        super(key, value);
    }
    
    @Override
    public void setValue(final Boolean value) {
        this.value = value;
    }
    
    @Override
    public Boolean getValue() {
        return this.value;
    }
}
