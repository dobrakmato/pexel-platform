package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Long {@link ConfigurationValue}.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class LongConfigurationValue extends ConfigurationValue<Long> {
    @XmlAttribute
    private final String type = "long";
    @XmlAttribute
    private long         value;
    
    // JAXB needs this.
    public LongConfigurationValue() {
    }
    
    public LongConfigurationValue(final String key, final long value) {
        super(key, value);
    }
    
    @Override
    public void setValue(final Long value) {
        this.value = value;
    }
    
    @Override
    public Long getValue() {
        return this.value;
    }
    
}
