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
package eu.matejkormuth.pexel.master.matchmaking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.ProxiedPlayer;
import eu.matejkormuth.pexel.network.ServerInfo;

/**
 * Basic implementation of matchmaking.
 */
public class MatchmakingProviderImpl extends MatchmakingProvider {
    /**
     * List of registered minigames.
     */
    protected final Set<String>                            minigames   = new HashSet<String>();
    /**
     * List of registered arenas.
     */
    protected final Map<String, List<MatchmakingGameImpl>> arenas      = new HashMap<String, List<MatchmakingGameImpl>>();
    /**
     * Mapping {@link UUID} (gameId) to games {@link MatchmakingGameImpl}.
     */
    protected final Map<UUID, MatchmakingGameImpl>         uuidMapping = new HashMap<UUID, MatchmakingGameImpl>();
    /**
     * List of players in matchmaking.
     */
    protected final List<ProxiedPlayer>                    players     = new ArrayList<ProxiedPlayer>();
    
    /**
     * Pending matchmaking request.
     */
    protected final List<MatchmakingRequest>               requests    = new ArrayList<MatchmakingRequest>();
    /**
     * List of request being removed in this iteration.
     */
    protected final List<MatchmakingRequest>               removing    = new ArrayList<MatchmakingRequest>();
    
    @Override
    public void cancelRequest(final MatchmakingRequest request) {
        // TODO Auto-generated method stub
        
    }
    
    private void chat(final ProxiedPlayer player, final String message) {
        PexelMaster.getInstance().getChatProvider().sendMessage(player, message);
    }
    
    @Override
    public void registerMinigame(final String minigame) {
        this.minigames.add(minigame);
    }
    
    @Override
    public void registerArena(final MatchmakingGameImpl game) {
        if (this.arenas.containsKey(game.getMinigameName())) {
            this.arenas.get(game.getMinigameName()).add(game);
            this.uuidMapping.put(game.getGameUUID(), game);
        }
        else {
            this.arenas.put(game.getMinigameName(), new ArrayList<MatchmakingGameImpl>(
                    Arrays.asList(game)));
            this.uuidMapping.put(game.getGameUUID(), game);
        }
    }
    
    @Override
    public void addRequest(final MatchmakingRequest request) {
        boolean safe = true;
        String playername = null;
        if (request.getMinigameName() != null) {
            for (ProxiedPlayer p : request.getPlayers()) {
                if (this.players.contains(p)) {
                    this.chat(
                            p,
                            "Can't register matchmaking request while in queue with another one! Type /leave to leave all requests.");
                    safe = false;
                    if (playername == null) {
                        playername = p.getDisplayName();
                    }
                    else {
                        playername += ", " + p.getDisplayName();
                    }
                }
            }
            
            if (safe) {
                request.tries = 0;
                this.requests.add(request);
                this.players.addAll(request.getPlayers());
            }
            else {
                for (ProxiedPlayer p : request.getPlayers()) {
                    PexelMaster.getInstance()
                            .getChatProvider()
                            .sendMessage(
                                    p,
                                    "Matchmaking failed! Player(s) '" + playername
                                            + ChatColor.RED
                                            + "' are in another matchmaking request!");
                }
            }
        }
        else {
            for (ProxiedPlayer p : request.getPlayers()) {
                PexelMaster.getInstance()
                        .getChatProvider()
                        .sendMessage(p, "Matchmaking failed! Invalid request!");
            }
        }
    }
    
    @Override
    public void doMatchmaking() {
        synchronized (this.requests) {
            this.removing.clear();
            
            // Stats
            int iterations = 0;
            int maxIterations = 256;
            int playercount = 0;
            int matchcount = 0;
            
            //Pokus sa sparovat vsetky poziadavky.
            for (MatchmakingRequest request : this.requests) {
                for (ProxiedPlayer p : request.getPlayers()) {
                    this.chat(p, ChatColor.GOLD + "Finding best matches ("
                            + (request.tries + 1) + ")... Please, be patient!");
                }
                
                request.tries++;
                if (request.tries >= 20) {
                    for (ProxiedPlayer p : request.getPlayers()) {
                        this.chat(p, "Matchmaking failed!");
                    }
                    this.removing.add(request);
                }
                
                //Ak sme neprekrocili limit.
                if (iterations > maxIterations)
                    break;
                
                if (request.getGame() != null) {
                    //The best function.
                    this.makeMatchesBySpecifiedMinigameAndMminigameArenaFromMatchMakingRequest_Version_1_0_0_0_1(request);
                }
                else {
                    List<MatchmakingGameImpl> minigame_arenas = this.arenas.get(request.getMinigameName());
                    
                    for (MatchmakingGameImpl arena : minigame_arenas) {
                        // If is not empty, and there is a place for them
                        if (!arena.empty() && arena.canJoin(request.playerCount())) {
                            // Connect all of them
                            for (ProxiedPlayer player : request.getPlayers()) {
                                arena.connectPlayer(player);
                            }
                            //Odstran request zo zoznamu.
                            this.removing.add(request);
                            break;
                        }
                    }
                    
                    for (MatchmakingGameImpl arena : minigame_arenas) {
                        // If is not empty, and there is a place for them
                        if (arena.canJoin(request.playerCount())) {
                            // Connect all of them
                            for (ProxiedPlayer player : request.getPlayers()) {
                                arena.connectPlayer(player);
                            }
                            // Remove request from queue.
                            this.removing.add(request);
                            break;
                        }
                    }
                }
                
                iterations++;
            }
            
            //Vymaz spracovane poziadavky zo zoznamu.
            for (MatchmakingRequest request : this.removing) {
                playercount += request.playerCount();
                matchcount++;
                this.players.removeAll(request.getPlayers());
                this.requests.remove(request);
            }
            
            if (playercount != 0) {
                this.log.info(
                        "Processed {0} players in {1} matches! {2} requests left.",
                        playercount, matchcount, this.requests.size());
            }
            
        }
    }
    
    // Must keep name.
    private void makeMatchesBySpecifiedMinigameAndMminigameArenaFromMatchMakingRequest_Version_1_0_0_0_1(
            final MatchmakingRequest request) {
        for (MatchmakingGameImpl arena : this.arenas.get(request.getMinigameName())) {
            // If is not empty, and there is a place for them
            if (!arena.empty() && arena.canJoin(request.playerCount())) {
                // Connect all of them
                for (ProxiedPlayer player : request.getPlayers()) {
                    arena.connectPlayer(player);
                }
                // Remove request from queue.
                this.removing.add(request);
                break;
            }
        }
        
        for (MatchmakingGameImpl arena : this.arenas.get(request.getMinigameName())) {
            // If is not empty, and there is a place for them
            if (arena.canJoin(request.playerCount())) {
                // Connect all of them
                for (ProxiedPlayer player : request.getPlayers()) {
                    arena.connectPlayer(player);
                }
                // Remove request from queue.
                this.removing.add(request);
                break;
            }
        }
    }
    
    @Override
    public Collection<MatchmakingGameImpl> getGames() {
        ArrayList<MatchmakingGameImpl> games = new ArrayList<MatchmakingGameImpl>(
                this.arenas.size() * 4);
        for (List<MatchmakingGameImpl> game : this.arenas.values()) {
            games.addAll(game);
        }
        return games;
    }
    
    @Override
    public MatchmakingGameImpl getGame(final UUID gameId) {
        return this.uuidMapping.get(gameId);
    }
    
    @Override
    public void unregisterSlaveGames(final ServerInfo slave) {
        for (Iterator<MatchmakingGameImpl> iterator = this.uuidMapping.values()
                .iterator(); iterator.hasNext();) {
            MatchmakingGameImpl game = iterator.next();
            if (game.host == slave) {
                this.log.info("Unregistering game {0} from matchmaking.",
                        game.gameId.toString());
                // Remove game from arena list.
                this.arenas.remove(game.getMinigameName()).remove(game);
                // Remove game from mapping.
                iterator.remove();
            }
        }
    }
}
