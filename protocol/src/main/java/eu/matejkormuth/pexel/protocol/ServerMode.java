package eu.matejkormuth.pexel.protocol;

/**
 * Class that specifies server modes.
 */
public enum ServerMode {
    /**
     * Everybody can join. Server shown as opened.
     */
    OPENED,
    /**
     * Only ops can join. Server shown as in maintenance.
     */
    IN_MAINTENANCE,
    /**
     * Only ops can join. Server shown as offline.
     */
    CLOSED;
}
