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
package eu.matejkormuth.pexel.slave.bukkit.util;

import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.arenas.AdvancedArena;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

/**
 * Formats and send messages to network (global) chat channel.
 */
public class NetworkCCFormatter {
    private static final String SEPARATOR                  = "|";
    
    public static final int     MSG_TYPE_ARENA_CONSTRUCTOR = 0;
    public static final int     MSG_TYPE_ARENA_CD_START    = 1;
    public static final int     MSG_TYPE_ARENA_CD_STOP     = 2;
    public static final int     MSG_TYPE_ARENA_PLAYERJOIN  = 3;
    public static final int     MSG_TYPE_ARENA_PLAYERLEAVE = 4;
    public static final int     MSG_TYPE_ARENA_PLAYERCOUNT = 5;
    public static final int     MSG_TYPE_ARENA_STATE       = 6;
    
    public static final void sendArenaMsg(final int type,
            final AdvancedArena arena, final String msg) {
        ChatManager.CHANNEL_NETWORK.broadcastMessage("ARENA-" + type
                + NetworkCCFormatter.SEPARATOR + NetworkCCFormatter.formatArena(arena)
                + NetworkCCFormatter.SEPARATOR + msg);
    }
    
    private static String formatArena(final AdvancedArena arena) {
        return arena.getName();
    }
    
    /**
     * Sends message to network chat channel.
     * 
     * @param type
     *            type of message - one of MSG_TYPE_* constants from {@link NetworkCCFormatter}
     * @param arena
     *            arena that executed this send
     * @param msg
     *            message to send
     */
    public static final void send(final int type, final AdvancedArena arena,
            final String msg) {
        NetworkCCFormatter.sendArenaMsg(type, arena, msg);
    }
    
    public static void sendConstructor(final AdvancedArena advancedMinigameArena) {
        NetworkCCFormatter.sendArenaMsg(MSG_TYPE_ARENA_CONSTRUCTOR,
                advancedMinigameArena, "Construct");
    }
    
    public static void sendCDstart(final AdvancedArena advancedMinigameArena) {
        NetworkCCFormatter.sendArenaMsg(MSG_TYPE_ARENA_CD_START, advancedMinigameArena,
                Long.toString(System.currentTimeMillis()));
    }
    
    public static void sendCDstop(final AdvancedArena advancedMinigameArena) {
        NetworkCCFormatter.sendArenaMsg(MSG_TYPE_ARENA_CD_STOP, advancedMinigameArena,
                Long.toString(System.currentTimeMillis()));
    }
    
    public static void sendPlayerJoin(final AdvancedArena advancedMinigameArena,
            final Player player) {
        NetworkCCFormatter.sendArenaMsg(MSG_TYPE_ARENA_PLAYERJOIN,
                advancedMinigameArena,
                player.getUniqueId().toString() + "/" + player.getName());
        NetworkCCFormatter.send(MSG_TYPE_ARENA_PLAYERCOUNT, advancedMinigameArena,
                Integer.toString(advancedMinigameArena.getPlayerCount()));
    }
    
    public static void sendPlayerLeft(final AdvancedArena advancedMinigameArena,
            final Player player) {
        NetworkCCFormatter.sendArenaMsg(MSG_TYPE_ARENA_PLAYERLEAVE,
                advancedMinigameArena,
                player.getUniqueId().toString() + "/" + player.getName());
        NetworkCCFormatter.send(MSG_TYPE_ARENA_PLAYERCOUNT, advancedMinigameArena,
                Integer.toString(advancedMinigameArena.getPlayerCount()));
    }
}
