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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

public class LoginCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("lobbyarena")) {
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
        sender.sendMessage(ChatManager.error("Wrong use!"));
        return true;
    }
    
    private void processCommand(final Player sender, final String[] args) {
        if (args.length != 1)
            sender.sendMessage(ChatManager.error("Wrong use! Syntax: /login <password>"));
        else
            Pexel.getAuth().authenticateCommand(sender, args[0]);
    }
    
    private void processOpCommand(final Player sender, final String[] args) {
        this.processCommand(sender, args);
    }
}
