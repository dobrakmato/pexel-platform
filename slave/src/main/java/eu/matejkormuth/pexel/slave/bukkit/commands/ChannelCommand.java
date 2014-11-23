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
