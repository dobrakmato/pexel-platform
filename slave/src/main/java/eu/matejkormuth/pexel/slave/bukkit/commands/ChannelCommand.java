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
package eu.matejkormuth.pexel.slave.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.chat.ChatChannel;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;
import eu.matejkormuth.pexel.slave.bukkit.chat.PlayerChannelSubscriber;
import eu.matejkormuth.pexel.slave.bukkit.chat.SubscribeMode;

/**
 * Class that handles execution of /channel commands.
 */
@CommandHandler(name = "channel")
public class ChannelCommand {
    
    @SubCommand(description = "Displays information about channels you are in.")
    public void main(final Player sender) {
        sender.sendMessage(ChatColor.GOLD + "Your channels: ");
        for (ChatChannel channel : ChatManager.getChannelsByPlayer(sender)) {
            sender.sendMessage(channel.getName());
        }
        sender.sendMessage(ChatColor.AQUA + "/channel help - Displays help information.");
    }
    
    @SubCommand(description = "Lists all avaiable chat channels.")
    public void list(final Player sender) {
        sender.sendMessage(ChatColor.GREEN + "Avaiable channels: ");
        for (ChatChannel channel : ChatManager.getChannelsByPlayer(sender)) {
            if (channel.isPublic())
                sender.sendMessage(channel.getName());
        }
    }
    
    @SubCommand(description = "Joins specified chat channel.")
    public void join(final Player sender, final String channelName) {
        if (ChatManager.getChannel(channelName) != null)
            if (!ChatManager.getChannel(channelName).isSubscribed(sender))
                
                ChatManager.getChannel(channelName).subscribe(
                        new PlayerChannelSubscriber(sender, SubscribeMode.READ));
            else
                sender.sendMessage(ChatManager.error("You are already in that channel!"));
        else
            sender.sendMessage(ChatManager.error("That channel does not exists!"));
    }
    
    @SubCommand(description = "Lefts specified chat channel.")
    public void leave(final Player sender, final String channelName) {
        if (ChatManager.getChannel(channelName).isSubscribed(sender))
            ChatManager.getChannel(channelName).unsubscribe(sender);
        else
            sender.sendMessage(ChatManager.error("You are not in that channel!"));
    }
}
