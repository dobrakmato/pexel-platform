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

/**
 * Command executor for /spawn command
 * 
 * @author Mato Kormuth
 * 
 */
public class SpawnCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(final CommandSender paramCommandSender,
            final Command paramCommand, final String paramString,
            final String[] paramArrayOfString) {
        if (paramCommand.getName().equalsIgnoreCase("spawn")) {
            ((Player) paramCommandSender).teleport(Pexel.getHubLocation());
            return true;
        }
        return false;
    }
}
