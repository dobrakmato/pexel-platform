package eu.matejkormuth.pexel.commons.configuration.values;

import eu.matejkormuth.pexel.commons.configuration.ConfigurationSection;

public class ConfigurationValueFactory {
    public ConfigurationValue<Integer> createInteger(final String key, final int value) {
        return new IntegerConfigurationValue(key, value);
    }
    
    public ConfigurationValue<Long> createLong(final String key, final long value) {
        return new LongConfigurationValue(key, value);
    }
    
    public ConfigurationValue<String> createString(final String key, final String value) {
        return new StringConfigurationValue(key, value);
    }
    
    public ConfigurationValue<Boolean> createBoolean(final String key,
            final boolean value) {
        return new BooleanConfigurationValue(key, value);
    }
    
    public ConfigurationValue<ConfigurationSection> createSection(final String key,
            final ConfigurationSection value) {
        return new SectionConfigurationValue(key, value);
    }
}
