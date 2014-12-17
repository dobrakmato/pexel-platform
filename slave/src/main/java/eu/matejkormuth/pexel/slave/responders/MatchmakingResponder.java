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
package eu.matejkormuth.pexel.slave.responders;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.protocol.requests.OutPlayerMatchmakedMessage;
import eu.matejkormuth.pexel.slave.LimboHandler;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.components.Matchmaking;
import eu.matejkormuth.pexel.slave.events.player.PlayerJoinEvent;

/**
 * Class that handles incoming requests.
 */
public class MatchmakingResponder {
    private final Map<UUID, OutPlayerMatchmakedMessage> matchmaked = new HashMap<UUID, OutPlayerMatchmakedMessage>();
    
    public MatchmakingResponder(final EventBus bus) {
        // Register events.
        bus.register(this);
    }
    
    public void onPlayerMatchmaked(final OutPlayerMatchmakedMessage msg) {
        Player player = PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(msg.playerId);
        if (player != null) {
            PexelSlave.getInstance()
                    .getComponent(Matchmaking.class)
                    .getArena(msg.gameId)
                    .join(player);
        }
        else {
            this.matchmaked.put(msg.playerId, msg);
        }
    }
    
    @Subscribe
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (this.matchmaked.containsKey(event.getPlayer().getUniqueId())) {
            if (System.currentTimeMillis() < this.matchmaked.get(event.getPlayer()
                    .getUniqueId()).createdAt + 5000) {
                PexelSlave.getInstance()
                        .getComponent(Matchmaking.class)
                        .getArena(
                                this.matchmaked.get(event.getPlayer().getUniqueId()).gameId)
                        .join(event.getPlayer());
            }
            else {
                // Timeout -> limbo
                LimboHandler.handle(event.getPlayer(), "matchmaking.timeout{p="
                        + event.getPlayer().getUniqueId().toString() + ",gameId="
                        + this.matchmaked.get(event.getPlayer().getUniqueId()).gameId
                        + "}");
            }
        }
        else {
            if (PexelSlave.getInstance().isGameOnlyServer()) {
                // Non OP players can't walk around on game servers.
                if (!event.getPlayer().isOp()) {
                    LimboHandler.handle(event.getPlayer(), "access.permissiondenied{p="
                            + event.getPlayer().getUniqueId().toString() + "}");
                }
            }
        }
        this.matchmaked.remove(event.getPlayer().getUniqueId());
    }
}
