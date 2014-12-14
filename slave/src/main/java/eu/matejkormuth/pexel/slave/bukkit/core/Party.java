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
package eu.matejkormuth.pexel.slave.bukkit.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.PlayerHolder;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.commons.minigame.Minigame;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

/**
 * Class used for party.
 */
public class Party implements PlayerHolder {
    private final List<Player> players;
    private final Player       owner;
    
    /**
     * Creates new party.
     * 
     * @param owner
     *            owner of the party
     */
    public Party(final Player owner) {
        this.players = new ArrayList<Player>();
        this.owner = owner;
        
        owner.sendMessage(ChatManager.success("Created new party!"));
    }
    
    /**
     * Returns whether is specified player owner of the party.
     * 
     * @param player
     *            specified player
     * @return true or false
     */
    public boolean isOwner(final Player player) {
        return this.owner == player;
    }
    
    /**
     * Adds player to party.
     * 
     * @param player
     *            player to be added
     */
    public void addPlayer(final Player player) {
        this.players.add(player);
        player.sendMessage(ChatManager.success("You have joined "
                + this.owner.getDisplayName() + "'s party!"));
    }
    
    /**
     * Makes {@link MatchmakingRequest} from this party.
     * 
     * @param minigame
     *            specified minigame
     * @param game
     *            specified arena
     * @return request
     */
    public MatchmakingRequest toRequest(final Minigame minigame,
            final MatchmakingGame game) {
        return new MatchmakingRequest(this.players, minigame.getName(), game);
    }
    
    @Override
    public Collection<Player> getPlayers() {
        return this.players;
    }
    
    @Override
    public int getPlayerCount() {
        return this.players.size();
    }
    
    public void removePlayer(final Player player) {
        this.players.remove(player);
        StorageEngine.getProfile(player.getUniqueId()).setParty(null);
    }
    
    @Override
    public boolean contains(final Player player) {
        return this.players.contains(player);
    }
}
