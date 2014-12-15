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
package eu.matejkormuth.pexel.commons.chat;

/**
 * Interface for chat channel subscriber.
 */
public interface ChannelSubscriber {
    /**
     * Sends message to this subscriber.
     * 
     * @param message
     *            message to be send
     */
    public void sendMessage(String message);
    
    /**
     * Returns this subscriber's mode.
     * 
     * @return subscribe mode
     */
    public SubscribeMode getMode();
    
    /**
     * Returns if is this subscriber online/active.
     * 
     * @return whether the player is online or not
     */
    public boolean isOnline();
    
    /**
     * Returns name of channel subscriber.
     * 
     * @return name of subscriber
     */
    public String getName();
}
