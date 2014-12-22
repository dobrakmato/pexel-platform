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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.text.ChatColor;

/**
 * Class specifing chat channel.
 */
public class ChatChannel {
    public static final String              UNSUBCRIBE_MSG = ChatColor.LIGHT_PURPLE
                                                                   + "You have left '%name%' chat channel!";
    public static final String              SUBCRIBE_MSG   = ChatColor.LIGHT_PURPLE
                                                                   + "You have joined '%name%' chat channel with mode %mode%!";
    public static ChatChannel               CHANNEL_LOG    = new ChatChannel("_log");
    public static ChatChannel               CHANNEL_GLOBAL = new ChatChannel("_global");
    public static ChatChannel               CHANNEL_LOBBY  = new ChatChannel("_lobby");
    
    //Last "random" channel ID.
    private static AtomicLong               randomId       = new AtomicLong(0L);
    private static Map<String, ChatChannel> mapping        = new HashMap<String, ChatChannel>();
    
    /**
     * Returns channel by name or null if channel not found.
     * 
     * @param name
     *            name of channel
     * @return channel object
     */
    public static ChatChannel getByName(final String name) {
        return ChatChannel.mapping.get(name);
    }
    
    public static Collection<ChatChannel> allChannels() {
        return ChatChannel.mapping.values();
    }
    
    /**
     * Name of channel.
     */
    private final String                  name;
    /**
     * List of subscribers.
     */
    private final List<ChannelSubscriber> subscribers  = new ArrayList<ChannelSubscriber>();
    /**
     * Prefix of this channel.
     */
    private String                        prefix       = "";
    /**
     * Date of last activity in this channel.
     */
    private long                          lastActivity = Long.MAX_VALUE;
    /**
     * Specifies if channel is visible to everyone.
     */
    private boolean                       isHidden     = false;
    
    /**
     * Creates new chat channel with specified name.
     * 
     * @param name
     *            name of channel
     */
    public ChatChannel(final String name) {
        this.name = name;
        ChatChannel.mapping.put(name, this);
    }
    
    public ChatChannel(final String name, final String prefix) {
        this.name = name;
        this.prefix = prefix;
        ChatChannel.mapping.put(name, this);
    }
    
    public ChatChannel(final String name, final String prefix, final boolean writable) {
        this.name = name;
        this.prefix = prefix;
        ChatChannel.mapping.put(name, this);
    }
    
    public ChatChannel(final String name, final boolean writable) {
        this.name = name;
        ChatChannel.mapping.put(name, this);
    }
    
    /**
     * Subscribes player to this chat channel.
     * 
     * @param player
     *            player
     */
    public void subscribe(final Player player, final SubscribeMode mode) {
        this.subscribers.add(new PlayerChannelSubscriber(player, mode));
        
        player.sendMessage(ChatChannel.SUBCRIBE_MSG.replace("%name%", this.getName())
                .replace("%mode%", mode.toString()));
    }
    
    /**
     * Subscribes subscriber to this chat channel.
     * 
     * @param subscriber
     *            subscriber to be added to this channel
     */
    public void subscribe(final ChannelSubscriber subscriber) {
        this.subscribers.add(subscriber);
        
        subscriber.sendMessage(ChatChannel.SUBCRIBE_MSG.replace("%name%", this.getName())
                .replace("%mode%", subscriber.getMode().toString()));
    }
    
    /**
     * Unsubscribes player from this chat channel.
     * 
     * @param player
     *            specified player
     */
    public void unsubscribe(final Player player) {
        for (Iterator<ChannelSubscriber> iterator = this.subscribers.iterator(); iterator.hasNext();) {
            ChannelSubscriber subscriber = iterator.next();
            if (subscriber instanceof PlayerChannelSubscriber)
                if (((PlayerChannelSubscriber) subscriber).getPlayer() == player)
                    iterator.remove();
        }
        
        player.sendMessage(ChatChannel.UNSUBCRIBE_MSG.replace("%name%", this.getName()));
    }
    
    /**
     * Unsubscribes specified subscriber from this channel. If the subscriber did not subscribed to this channel,
     * nothing happens.
     * 
     * @param subscriber
     *            subscrber to be unregistered
     */
    public void unsubscribe(final ChannelSubscriber subscriber) {
        this.subscribers.remove(subscriber);
        
        subscriber.sendMessage(ChatChannel.UNSUBCRIBE_MSG.replace("%name%",
                this.getName()));
    }
    
    /**
     * Retruns true, if specified subscriber is subscribed.
     * 
     * @param subscriber
     *            subscriber to check
     */
    public boolean isSubscribed(final ChannelSubscriber subscriber) {
        return this.subscribers.contains(subscriber);
    }
    
    /**
     * Returns whether player can read messages from this channel.
     * 
     * @param player
     *            player to check
     * @return true if player can read
     */
    public boolean canRead(final Player player) {
        for (ChannelSubscriber subscriber : this.subscribers)
            if (subscriber instanceof PlayerChannelSubscriber)
                if (((PlayerChannelSubscriber) subscriber).getPlayer() == player)
                    return this.canRead(subscriber);
        return false;
    }
    
    /**
     * Returns whether subscriber can read messages from this channel.
     * 
     * @param uuid
     *            player to check
     * @return true if player can read
     */
    public boolean canRead(final ChannelSubscriber subscriber) {
        return this.subscribers.contains(subscriber);
    }
    
    /**
     * Sends message to all subscribers.
     * 
     * @param message
     *            message to be send
     */
    public void broadcastMessage(String message) {
        this.lastActivity = System.currentTimeMillis();
        // Support for colored messages
        message = ChatColor.translateAlternateColorCodes("&".toCharArray()[0], message);
        for (Iterator<ChannelSubscriber> iterator = this.subscribers.iterator(); iterator.hasNext();) {
            ChannelSubscriber p = iterator.next();
            if (p.isOnline()) {
                if (message.toLowerCase().contains(p.getName().toLowerCase())
                        && !message.startsWith(p.getName().toLowerCase())) {
                    p.sendMessage(this.prefix + ChatColor.BLUE + message);
                    if (p instanceof PlayerChannelSubscriber) {
                        //((PlayerChannelSubscriber) p).getPlayer().playSound(
                        //        ((PlayerChannelSubscriber) p).getPlayer().getLocation(),
                        //        Sound.LEVEL_UP, 0.5F, 1);
                        // TODO: Add support for sounds.
                    }
                }
                else {
                    p.sendMessage(this.prefix + message);
                }
            }
            else {
                iterator.remove();
            }
        }
    }
    
    /**
     * Returns name of channel.
     * 
     * @return the name of channel
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns if player can write to this channel.
     * 
     * @param player
     * @return true if player can write
     */
    public boolean canWrite(final Player player) {
        for (ChannelSubscriber subscriber : this.subscribers)
            if (subscriber instanceof PlayerChannelSubscriber)
                if (((PlayerChannelSubscriber) subscriber).getPlayer() == player)
                    return this.canWrite(subscriber);
        return false;
    }
    
    /**
     * Returns if player can write to this channel.
     * 
     * @param subscriber
     *            specified subscriber
     * @return true if player can write
     */
    public boolean canWrite(final ChannelSubscriber subscriber) {
        if (!this.subscribers.contains(subscriber))
            return false;
        else {
            return subscriber.getMode() == SubscribeMode.READ_WRITE;
        }
    }
    
    /**
     * Returns random new channel, without specified name and stuff.
     * 
     * @return random chat channel
     */
    public static ChatChannel createRandom() {
        return new ChatChannel("r" + ChatChannel.randomId.getAndIncrement());
    }
    
    /**
     * Set's channels prefix.
     */
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    /**
     * @return the lastActivity
     */
    public long getLastActivity() {
        return this.lastActivity;
    }
    
    /**
     * @param player
     */
    public boolean isSubscribed(final Player player) {
        for (ChannelSubscriber subscriber : this.subscribers)
            if (subscriber instanceof PlayerChannelSubscriber)
                if (((PlayerChannelSubscriber) subscriber).getPlayer() == player)
                    return true;
        return false;
    }
    
    /**
     * Returns whether this channel is hidden.
     */
    public boolean isHidden() {
        return this.isHidden;
    }
    
    /**
     * @param isHidden
     *            the isHidden to set
     */
    public void setPublic(final boolean isPublic) {
        this.isHidden = isPublic;
    }
}
