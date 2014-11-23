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
package eu.matejkormuth.pexel.slave.bukkit.chat;

import org.bukkit.entity.Player;

/**
 * Chat channel subscriber.
 * 
 * @author Mato Kormuth
 * 
 */
public class PlayerChannelSubscriber implements ChannelSubscriber {
    private final SubscribeMode mode;
    private final Player        player;
    
    /**
     * Creates new instance of player channel subscriber.
     * 
     * @param player
     *            player
     * @param mode
     *            subscribe mode
     */
    public PlayerChannelSubscriber(final Player player, final SubscribeMode mode) {
        this.mode = mode;
        this.player = player;
    }
    
    /**
     * Returns subscription mode.
     * 
     * @return subscription mode
     */
    @Override
    public SubscribeMode getMode() {
        return this.mode;
    }
    
    /**
     * Sends message to this subscriber.
     * 
     * @param message
     *            message to send
     */
    @Override
    public void sendMessage(final String message) {
        this.player.sendMessage(message);
    }
    
    @Override
    public boolean isOnline() {
        return this.player.isOnline();
    }
    
    /**
     * Returns the player to which is this subscriber associated.
     * 
     * @return player object
     */
    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public String getName() {
        return this.player.getName();
    }
}
