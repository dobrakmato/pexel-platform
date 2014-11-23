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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;
import eu.matejkormuth.pexel.slave.bukkit.core.Settings;
import eu.matejkormuth.pexel.slave.bukkit.core.StorageEngine;

/**
 * Class used for settings.
 * 
 * @author Mato Kormuth
 * 
 */
public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String paramString, final String[] args) {
        if (sender instanceof Player) {
            if (args.length == 2) {
                try {
                    Settings setting = Settings.valueOf(args[0]);
                    Boolean value = Boolean.parseBoolean(args[1]);
                    
                    StorageEngine.getProfile(((Player) sender).getUniqueId()).setSetting(
                            setting, value);
                    
                } catch (Exception ex) {
                    sender.sendMessage(ChatManager.error(ex.toString()));
                }
            }
            else {
                sender.sendMessage(ChatManager.error("/settings <setting> <false/true>"));
                String avaiable = ChatColor.GOLD + "Avaiable settings: "
                        + ChatColor.YELLOW;
                for (Settings s : Settings.values()) {
                    avaiable += s.toString() + ", ";
                }
                sender.sendMessage(avaiable);
            }
        }
        return false;
    }
}
