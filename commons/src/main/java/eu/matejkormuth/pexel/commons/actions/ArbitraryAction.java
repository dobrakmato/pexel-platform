// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
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
