package eu.matejkormuth.pexel.slave.events;

/**
 * Represents Cancellable.
 */
public interface Cancellable {
    /**
     * Sets cancelled state of this event.
     * 
     * @param cancelled
     *            true if event should be canceled
     */
    public void setCancelled(boolean cancelled);
    
    /**
     * Returns whether this event is canceled.
     * 
     * @return true if event should be canceled
     */
    public boolean isCanceled();
}
