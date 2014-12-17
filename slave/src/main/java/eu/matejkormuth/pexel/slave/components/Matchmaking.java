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
package eu.matejkormuth.pexel.slave.components;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Preconditions;

import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.commons.minigame.Minigame;
import eu.matejkormuth.pexel.protocol.requests.InMatchmakingRegisterGameMessage;
import eu.matejkormuth.pexel.protocol.requests.InMatchmakingRequest;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.SlaveComponent;

/**
 * Slave side implementation of maatchmaking.
 */
public class Matchmaking extends SlaveComponent {
    
    private final Set<String>             minigames     = new HashSet<String>();
    private final Map<String, SlaveArena> arenas_byTag  = new HashMap<String, SlaveArena>();
    private final Map<UUID, SlaveArena>   arenas_byUUID = new HashMap<UUID, SlaveArena>();
    
    public void addRequest(final MatchmakingRequest request) {
        PexelSlave.getInstance()
                .getServer()
                .getMasterServerInfo()
                .sendRequest(new InMatchmakingRequest(request));
    }
    
    public void registerGame(final Minigame minigame) {
        Preconditions.checkNotNull(minigame);
        
        this.minigames.add(minigame.getName());
    }
    
    public Set<String> getMinigames() {
        return this.minigames;
    }
    
    public SlaveArena getArena(final String tag) {
        return this.arenas_byTag.get(tag);
    }
    
    public SlaveArena getArena(final UUID gameId) {
        return this.arenas_byUUID.get(gameId);
    }
    
    public Collection<SlaveArena> getArenas() {
        return this.arenas_byTag.values();
    }
    
    public void registerArena(final SlaveArena arena) {
        Preconditions.checkNotNull(arena);
        
        this.arenas_byTag.put(arena.getTag(), arena);
        this.arenas_byUUID.put(arena.getGameUUID(), arena);
    }
    
    /**
     * Registers all arenas on master matchmaking. Called from {@link PexelSlave} automatically.
     */
    public void registerGamesOnMaster() {
        this.getLogger().info("Registering all games on master matchmaking...");
        int count = 0;
        for (SlaveArena arena : this.getArenas()) {
            // Register each game on master.
            PexelSlave.getInstance()
                    .getServer()
                    .getMasterServerInfo()
                    .sendRequest(
                            new InMatchmakingRegisterGameMessage(arena.getGameUUID(),
                                    arena.getMinigame().getName()));
            count++;
        }
        this.getLogger().info(
                "Sent " + count + " InMatchmakingRegisterGameMessage requests!");
    }
}
