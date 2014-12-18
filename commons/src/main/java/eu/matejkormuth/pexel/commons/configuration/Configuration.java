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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that provides configuration.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration extends Unmarshaller.Listener {
    protected transient File                              file;
    protected transient Map<String, ConfigurationSection> sections;
    protected List<ConfigurationSection>                  section = new ArrayList<ConfigurationSection>();
    
    public Configuration() {
    }
    
    public Configuration(final File file) {
        this.file = file;
        this.sections = new HashMap<String, ConfigurationSection>();
    }
    
    /**
     * Returns configuration section or if sections does not exists, it creates empty one.
     * 
     * @return configuration section
     */
    public ConfigurationSection getSection(final String key) {
        if (this.sections == null) {
            this.afterUnmarshal(null, null);
        }
        if (this.sections.containsKey(key)) { return this.sections.get(key); }
        return this.createSection(key);
    }
    
    /**
     * Returns configuration section or if sections does not exists, it creates empty one.
     * 
     * @return configuration section
     */
    public ConfigurationSection getSection(final Class<?> clazz) {
        return this.getSection(clazz.getCanonicalName());
    }
    
    /**
     * Creates section for specified key.
     * 
     * @param key
     *            key
     * @return newwly created configuration section
     */
    public ConfigurationSection createSection(final String key) {
        if (this.sections.containsKey(key)) { throw new RuntimeException(
                "Section already exists!"); }
        ConfigurationSection section = new ConfigurationSection(key);
        this.section.add(section);
        this.sections.put(key, section);
        return section;
    }
    
    /**
     * Saves this configuration to file that was loaded from. (Can throw exception, if called on non-from file loaded
     * Configuration).
     */
    public void save() {
        this.save(this.file);
    }
    
    /**
     * Saves this configuration to specified file.
     * 
     * @param file
     *            file to save configuration.
     */
    public void save(final File file) {
        try {
            JAXBContext cont = JAXBContext.newInstance(Configuration.class);
            javax.xml.bind.Marshaller m = cont.createMarshaller();
            m.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(this, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
            Unmarshaller un = cont.createUnmarshaller();
            un.setListener(conf);
            conf = (Configuration) un.unmarshal(file);
            conf.file = file;
            return conf;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void afterUnmarshal(final Object target, final Object parent) {
        //Create map.
        this.sections = new HashMap<String, ConfigurationSection>(this.section.size());
        for (ConfigurationSection section : this.section) {
            this.sections.put(section.getKey(), section);
        }
    }
    
    /**
     * Class that specifies all default valid configuration keys.
     */
    public static final class Keys {
        public static final String KEY_AUTHKEY              = "authKey";
        public static final String KEY_PORT                 = "port";
        public static final String KEY_PORT_API_HTTP        = "portApiHttp";
        public static final String KEY_PORT_API_HTTPS       = "portApiHttps";
        public static final String KEY_MASTER_IP            = "masterIp";
        public static final String KEY_SLAVE_NAME           = "slaveName";
        public static final String KEY_LIMBO_SERVER_NAME    = "limboServer";
        // StorageImpl keys.
        public static final String KEY_STORAGE_AUTOUPDATES  = "autoUpdates";
        public static final String KEY_STORAGE_ONLYTRUSTED  = "onlyTrustedSources";
        // Matchmaking keys.
        public static final String KEY_MATCHMAKING_INTERVAL = "interval";
        // Database keys.
        public static final String KEY_DATABASE_URL         = "url";
        public static final String KEY_DATABASE_USERNAME    = "user";
        public static final String KEY_DATABASE_PASSWORD    = "password";
    }
    
    public static final class Defaults {
        public static final String AUTH_KEY          = "hIFgRmfHZbncbkFgT36H3m4ENlcyoSTwqoC8BHFqJTsL3XRNQNkK0feDqh2FZM1g0uer2KHBu0coOU1vxc5oh9SyhK36mVddfiv8S3zcTCrxmiSKkOYsOHRViLRvwVyC";
        public static final String DATABASE_URL      = "jdbc:mysql://127.0.0.1/pexel";
        public static final String DATABASE_USERNAME = "root";
        public static final String DATABASE_PASSWORD = "root";
    }
}
