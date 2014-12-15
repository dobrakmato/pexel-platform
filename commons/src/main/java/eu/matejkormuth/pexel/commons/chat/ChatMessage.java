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

import org.bukkit.ChatColor;

/**
 * Class that represents chat message.
 */
public class ChatMessage {
    private String            rawMessage;
    private ChannelSubscriber sender;
    
    /**
     * @param rawMessage
     * @param sender
     */
    public ChatMessage(final String rawMessage, final ChannelSubscriber sender) {
        this.rawMessage = rawMessage;
        this.sender = sender;
    }
    
    /**
     * @return the rawMessage
     */
    public String getRawMessage() {
        return this.rawMessage;
    }
    
    /**
     * @param rawMessage
     *            the rawMessage to set
     */
    public void setRawMessage(final String rawMessage) {
        this.rawMessage = rawMessage;
    }
    
    /**
     * @return the sender
     */
    public ChannelSubscriber getSender() {
        return this.sender;
    }
    
    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(final ChannelSubscriber sender) {
        this.sender = sender;
    }
    
    public String getFormattedMessage() {
        return ChatColor.GRAY + this.sender.getName() + " > " + this.rawMessage;
    }
}
