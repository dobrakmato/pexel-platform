package eu.matejkormuth.pexel.network;

/**
 * Annotation that specifies request target.
 */
public @interface MessageTarget {
    /**
     * Type of server, that message is send to.
     */
    public ServerType value();
}
