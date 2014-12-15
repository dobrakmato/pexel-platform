package eu.matejkormuth.pexel.slave;

/**
 * Interaface that represents tick handler.
 */
public interface TickHandler {
    /**
     * Fired when server tick occurs.
     * 
     * @param number
     *            of tick currently executed
     */
    public void tick(long number);
}
