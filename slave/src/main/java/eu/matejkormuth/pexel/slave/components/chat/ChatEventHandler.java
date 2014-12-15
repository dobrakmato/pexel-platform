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
package eu.matejkormuth.pexel.slave.components.chat;

import com.google.common.eventbus.Subscribe;

import eu.matejkormuth.pexel.commons.chat.ChatChannel;
import eu.matejkormuth.pexel.commons.chat.SubscribeMode;
import eu.matejkormuth.pexel.slave.events.player.PlayerJoinEvent;
import eu.matejkormuth.pexel.slave.events.player.PlayerLeaveEvent;

/**
 * Chat event handler.
 */
public class ChatEventHandler {
    
    @Subscribe
    public void onPlayerJoin(final PlayerJoinEvent event) {
        // Join chat channels.
        ChatChannel.CHANNEL_GLOBAL.subscribe(event.getPlayer(), SubscribeMode.READ);
        ChatChannel.CHANNEL_LOBBY.subscribe(event.getPlayer(), SubscribeMode.READ_WRITE);
    }
    
    @Subscribe
    public void onPlayerLeave(final PlayerLeaveEvent event) {
        // Leave chat channels.
        ChatChannel.CHANNEL_GLOBAL.unsubscribe(event.getPlayer());
        ChatChannel.CHANNEL_LOBBY.unsubscribe(event.getPlayer());
    }
}
