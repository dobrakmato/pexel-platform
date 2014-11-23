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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;
import eu.matejkormuth.pexel.slave.bukkit.core.StorageEngine;

/**
 * @author Mato Kormuth
 * 
 */
public class UnfriendCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String paramString, final String[] args) {
        if (command.getName().equalsIgnoreCase("unfriend")) {
            if (sender instanceof Player) {
                if (sender.isOp()) {
                    this.processOpCommand((Player) sender, args);
                }
                else {
                    this.processCommand((Player) sender, args);
                }
            }
            else {
                sender.sendMessage(ChatManager.error("This command is only avaiable for players!"));
            }
            return true;
        }
        return false;
    }
    
    private void processCommand(final Player sender, final String[] args) {
        if (args.length >= 1) {
            String playerName = args[0];
            boolean success = false;
            
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equalsIgnoreCase(playerName)) {
                    if (StorageEngine.getProfile(sender.getUniqueId()).isFriend(
                            p.getUniqueId())) {
                        StorageEngine.getProfile(sender.getUniqueId()).removeFriend(
                                p.getUniqueId());
                        sender.sendMessage(ChatManager.success("Player '" + p.getName()
                                + "' has been REMOVED from your friends!"));
                        success = true;
                    }
                    else {
                        sender.sendMessage(ChatManager.error("Player '" + p.getName()
                                + "' is not in your friends list!"));
                        
                    }
                }
            }
            if (!success)
                sender.sendMessage(ChatManager.error("Player not found! Player must be online!"));
        }
        else {
            sender.sendMessage(ChatManager.error("You must provide player name!"));
        }
    }
    
    private void processOpCommand(final Player sender, final String[] args) {
        //No op commands.
        this.processCommand(sender, args);
    }
    
}
