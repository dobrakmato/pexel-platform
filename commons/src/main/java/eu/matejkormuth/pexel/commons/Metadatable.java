package eu.matejkormuth.pexel.commons;

/**
 * Interface that specifies basic metadata functions.
 */
public interface Metadatable {
    /**
     * Set's metadata value by key.
     * 
     * @param key
     *            metadata key
     * @param value
     *            metadata value
     */
    public void setMetadata(String key, String value);
    
    /**
     * Returns metadata value by key.
     * 
     * @param key
     *            metadata key
     * @return metadata value
     */
    public String getMetadata(String key);
}
