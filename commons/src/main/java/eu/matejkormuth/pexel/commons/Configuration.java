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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.math.RandomUtils;

import eu.matejkormuth.pexel.network.ServerType;

/**
 * Class that provides configuration.
 */
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration {
    protected transient Map<String, String> data  = new HashMap<String, String>();
    protected List<ConfigurationEntry>      entry = new ArrayList<ConfigurationEntry>();
    
    public Configuration() {
        
    }
    
    /**
     * Returns values by specified key.
     * 
     * @param key
     *            key
     * @return value
     */
    public String get(final String key) {
        return this.data.get(key);
    }
    
    public String getAsString(final String key) {
        return this.data.get(key).toString();
    }
    
    public int getAsInt(final String key) {
        return Integer.parseInt(this.data.get(key));
    }
    
    /**
     * Set's value by key.
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public void set(final String key, final String value) {
        this.data.put(key, value);
    }
    
    /**
     * Saves this configuration to specified file.
     * 
     * @param file
     *            file to save configuration.
     */
    public void save(final File file) {
        for (String key : this.data.keySet()) {
            this.entry.add(new ConfigurationEntry(key, this.data.get(key)));
        }
        try {
            JAXBContext cont = JAXBContext.newInstance(Configuration.class);
            javax.xml.bind.Marshaller m = cont.createMarshaller();
            m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(this, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        this.entry.clear();
    }
    
    /**
     * Loads configuration from file.
     * 
     * @param file
     *            file
     * @return configuration
     */
    public static Configuration load(final File file) {
        Configuration conf = new Configuration();
        try {
            JAXBContext cont = JAXBContext.newInstance(Configuration.class);
            conf = (Configuration) cont.createUnmarshaller().unmarshal(file);
            
            for (ConfigurationEntry entry : conf.entry) {
                conf.data.put(entry.key, entry.value);
            }
            
            return conf;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    @XmlType(name = "entry")
    protected static class ConfigurationEntry {
        public ConfigurationEntry() {
            
        }
        
        public ConfigurationEntry(final String key, final String value) {
            this.key = key;
            this.value = value;
        }
        
        @XmlAttribute(name = "key")
        public String key;
        @XmlAttribute(name = "value")
        public String value;
    }
    
    public static final String KEY_AUTHKEY             = "authKey";
    public static final String KEY_PORT                = "port";
    public static final String KEY_PORT_API_HTTP       = "portApiHttp";
    public static final String KEY_PORT_API_HTTPS      = "portApiHttps";
    public static final String KEY_MASTER_IP           = "masterIp";
    public static final String KEY_SLAVE_NAME          = "slaveName";
    public static final String KEY_LIMBO_SERVER_NAME   = "limboServer";
    // Storage keys.
    public static final String KEY_STORAGE_AUTOUPDATES = "autoUpdates";
    public static final String KEY_STORAGE_ONLYTRUSTED = "onlyTrustedSources";
    
    public static void createDefault(final ServerType type, final File f) {
        Configuration c = new Configuration();
        if (type == ServerType.MASTER) {
            c.set(Configuration.KEY_AUTHKEY,
                    "replace_this_default_authkey_with_custom_one_long_128_cahracters_You_can_find_generator_at_http://pexel.eu/platform_____________");
            c.set(Configuration.KEY_PORT, "29631");
            c.set(Configuration.KEY_PORT_API_HTTP, "10361");
            c.set(Configuration.KEY_PORT_API_HTTPS, "10362");
        }
        else {
            c.set(Configuration.KEY_AUTHKEY,
                    "replace_this_default_authkey_with_custom_one_long_128_cahracters_You_can_find_generator_at_http://pexel.eu/platform_____________");
            c.set(Configuration.KEY_PORT, "29631");
            c.set(Configuration.KEY_MASTER_IP, "0.0.0.0");
            c.set(Configuration.KEY_SLAVE_NAME, "coolslave" + RandomUtils.nextInt());
        }
        
        c.save(f);
    }
}
