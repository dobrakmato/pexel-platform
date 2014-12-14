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
package eu.matejkormuth.pexel.slave;

import eu.matejkormuth.pexel.commons.arenas.Arena;
import eu.matejkormuth.pexel.commons.arenas.ArenaState;
import eu.matejkormuth.pexel.protocol.requests.InGameStateChangedMessage;
import eu.matejkormuth.pexel.protocol.requests.InMatchmakingRegisterGameMessage;
import eu.matejkormuth.pexel.slave.Scheduler.ScheduledTask;

/**
 * Represents minigame arena, that is participating in matchmaking.
 */
public abstract class SlaveArena extends Arena {
    private ScheduledTask task;
    
    public SlaveArena(final String minigame) {
        // Register events in this arena.
        PexelSlave.getInstance().getEventBus().register(this);
        
        // Register this game on master.
        PexelSlave.getInstance().server.getMasterServerInfo().sendRequest(
                new InMatchmakingRegisterGameMessage(this.getUUID(), minigame));
    }
    
    @Override
    protected void startCountdown0() {
        this.task = PexelSlave.getInstance().getScheduler().each(20L, new Runnable() {
            @Override
            public void run() {
                SlaveArena.this.doCountdown();
            }
        });
    }
    
    @Override
    protected void stopCountdown0() {
        this.task.cancel();
    }
    
    @Override
    public void setState(final ArenaState state) {
        // Listen for changes and redirect them to master.
        PexelSlave.getInstance().server.getMasterServerInfo().sendRequest(
                new InGameStateChangedMessage(this.getUUID(), state));
        
        super.setState(state);
    }
}
