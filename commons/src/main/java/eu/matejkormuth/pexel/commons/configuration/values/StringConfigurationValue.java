package eu.matejkormuth.pexel.commons.configuration.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * String {@link ConfigurationValue}.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class StringConfigurationValue extends ConfigurationValue<String> {
    @XmlAttribute
    private final String type = "string";
    @XmlAttribute
    private String       value;
    
    // JAXB needs this.
    public StringConfigurationValue() {
    }
    
    public StringConfigurationValue(final String key, final String value) {
        super(key, value);
    }
    
    @Override
    public void setValue(final String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
}
