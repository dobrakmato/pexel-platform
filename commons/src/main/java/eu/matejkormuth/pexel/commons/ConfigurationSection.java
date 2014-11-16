package eu.matejkormuth.pexel.commons;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;

/**
 * Section of configuration.
 */
@XmlType(name = "section")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigurationSection {
    @XmlList
    protected List<ConfigurationEntry>                  entry;
    protected transient Map<String, ConfigurationEntry> map;
    
    public ConfigurationEntry get(final String key) {
        return null;
    }
    
    public void put(final ConfigurationEntry entry) {
        this.entry.add(entry);
    }
    
    public void remove(final String key) {
        for (Iterator<ConfigurationEntry> iterator = this.entry.iterator(); iterator.hasNext();) {
            ConfigurationEntry ce = iterator.next();
            if (ce.key.equals(key)) {
                iterator.remove();
            }
        }
    }
}
