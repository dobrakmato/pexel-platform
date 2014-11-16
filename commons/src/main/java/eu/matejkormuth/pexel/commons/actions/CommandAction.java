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
package eu.matejkormuth.pexel.commons.actions;

import org.bukkit.entity.Player;

/**
 * Basic sudo command player action.
 */
public class CommandAction implements Action {
    private String command = "";
    
    /**
     * Creates a new command action. Command should <b> not contain</b> slash. <code>%player%</code> in command will be
     * replaced with name of player, that is this command executing for.
     * 
     * @param command
     *            command of this action
     */
    public CommandAction(final String command) {
        this.command = command;
    }
    
    @Override
    public void execute(final Player player) {
        player.performCommand(this.command.replace("%player%", player.getName()));
    }
}
