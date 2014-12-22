package eu.matejkormuth.pexel.commons.actions;

import java.util.Collection;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Represents arbitrary action that is executed using {@link ArbitraryActionExecutor} action extender. This class can be
 * used when programmer is too lazy to create new class derived from {@link Action}.
 */
public class ArbitraryAction implements Action {
    private final ArbitraryActionExecutor executor;
    
    /**
     * Constructs new {@link ArbitraryAction} using specified anonymouse {@link ArbitraryActionExecutor}.
     * 
     * @param executor
     *            executor that specifies how the action will be executed
     */
    public ArbitraryAction(final ArbitraryActionExecutor executor) {
        this.executor = executor;
    }
    
    @Override
    public void execute(final Player player) {
        this.executor.execute(player);
    }
    
    @Override
    public void execute(final Collection<Player> players) {
        for (Player player : players) {
            this.executor.execute(player);
        }
    }
    
    /**
     * Represents anonymouse {@link Action} extender for {@link ArbitraryAction}.
     */
    public static interface ArbitraryActionExecutor {
        void execute(Player player);
    }
}
