package eu.matejkormuth.pexel.slave;

/**
 * Interaface that specifies commitable type.
 */
public interface Commitable {
    /**
     * Updates values in data storage (database).
     */
    public void commit();
}
