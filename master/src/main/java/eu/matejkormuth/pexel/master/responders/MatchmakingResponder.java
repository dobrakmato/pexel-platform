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
package eu.matejkormuth.pexel.master.responders;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.master.matchmaking.Matchmaking;
import eu.matejkormuth.pexel.master.matchmaking.MatchmakingGameImpl;
import eu.matejkormuth.pexel.protocol.requests.InGameStateChangedMessage;
import eu.matejkormuth.pexel.protocol.requests.InMatchmakingRegisterGameMessage;
import eu.matejkormuth.pexel.protocol.requests.InMatchmakingRequest;

/**
 * Matchmaking requests responder.
 */
public class MatchmakingResponder {
    public void onInMatchmakingRegisterGameMessage(
            final InMatchmakingRegisterGameMessage msg) {
        MatchmakingGameImpl game = new MatchmakingGameImpl(msg.getSender(), msg.gameId,
                msg.minigame);
        PexelMaster.getInstance().getComponent(Matchmaking.class).registerArena(game);
    }
    
    public void onInMatchmakingRequest(final InMatchmakingRequest msg) {
        Matchmaking matchmaking = PexelMaster.getInstance().getComponent(
                Matchmaking.class);
        Iterable<String> parts1 = Splitter.on("|").split(msg.msg);
        String minigameName = parts1.iterator().next();
        UUID gameId = null;
        try {
            gameId = UUID.fromString(parts1.iterator().next());
        } catch (Exception ex) {
            // Catch silently...
        }
        
        List<Player> players = Lists.newArrayList();
        for (String s : Splitter.on(',').split(parts1.iterator().next())) {
            PexelMaster.getInstance().getProxy().getPlayer(UUID.fromString(s));
        }
        
        matchmaking.registerRequest(new MatchmakingRequest(players, minigameName,
                matchmaking.getGame(gameId)));
        
    }
    
    public void onGameStatusChage(final InGameStateChangedMessage msg) {
        PexelMaster.getInstance().getComponent(Matchmaking.class).getGame(msg.gameId).cached_state = msg.newState;
    }
}
