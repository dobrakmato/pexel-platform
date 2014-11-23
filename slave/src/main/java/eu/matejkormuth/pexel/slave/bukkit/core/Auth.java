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
package eu.matejkormuth.pexel.slave.bukkit.core;

import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

/**
 * Class used for offline mode authentication.
 */
public class Auth {
    public void authenticateCommand(final Player player, final String password) {
        // TODO:  PHP or MYSQL Implementation to integrate with unified login system.
        
        // Unfreeze player.
        Pexel.getPlayerFreezer().unfreeze(player);
        
        player.sendMessage(ChatManager.success("Successfully logged in!"));
    }
    
    public void authenticateIp(final Player player, final String hostname) {
        // TODO:  PHP or MYSQL Implementation to integrate with unified login system.
    }
}
