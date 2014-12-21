package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Integer {@link ConfigurationValue}.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class IntegerConfigurationValue extends ConfigurationValue<Integer> {
    @XmlAttribute
    private final String type = "int";
    @XmlAttribute
    private int          value;
    
    // JAXB needs this.
    public IntegerConfigurationValue() {
    }
    
    public IntegerConfigurationValue(final String key, final int value) {
        super(key, value);
    }
    
    @Override
    public Integer getValue() {
        return this.value;
    }
    
    @Override
    public void setValue(final Integer value) {
        this.value = value;
    }
}
