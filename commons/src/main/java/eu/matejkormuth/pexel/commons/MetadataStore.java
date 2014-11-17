package eu.matejkormuth.pexel.commons;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * Class that holds metadata.
 */
public class MetadataStore implements Metadatable {
    private static Type               MAP_TYPE = new TypeToken<Map<String, String>>() {
                                               }.getType();
    private final Map<String, String> internal;
    
    private MetadataStore() {
        this(new HashMap<String, String>(10));
    }
    
    private MetadataStore(final Map<String, String> metadata) {
        this.internal = metadata;
    }
    
    public MetadataStore(final String json) {
        this.internal = Providers.JSON.fromJson(json, MetadataStore.MAP_TYPE);
    }
    
    public static MetadataStore create() {
        return new MetadataStore();
    }
    
    public static MetadataStore create(final String key, final String value) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return new MetadataStore(map);
    }
    
    public static MetadataStore create(final Map<String, String> metadata) {
        return new MetadataStore(metadata);
    }
    
    /**
     * Converts metadata to json string.
     * 
     * @return json encoded metadata
     */
    public String toJSON() {
        return Providers.JSON.toJson(this.internal);
    }
    
    @Override
    public void setMetadata(final String key, final String value) {
        this.internal.put(key, value);
    }
    
    @Override
    public String getMetadata(final String key) {
        return this.internal.get(key);
    }
    
}
