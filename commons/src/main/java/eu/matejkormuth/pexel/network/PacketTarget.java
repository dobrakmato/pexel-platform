package eu.matejkormuth.pexel.network;

/**
 * Annotation that specifies request target.
 */
public @interface PacketTarget {
    /**
     * Type of server, that message is send to.
     */
    public ServerType value();
}
