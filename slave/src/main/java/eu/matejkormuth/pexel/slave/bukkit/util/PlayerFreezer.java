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
package eu.matejkormuth.pexel.slave.bukkit.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import eu.matejkormuth.pexel.slave.boot.PexelSlaveBukkitPlugin;

/**
 * Utility class that help freezing of players.
 * 
 * @author Mato Kormuth
 * 
 */
public class PlayerFreezer implements Listener {
    /**
     * List of frozen textures. <br/>
     * FIXME: Remove 'dead' disconnected player objects.
     */
    private final List<Player> frozen_movement = new ArrayList<Player>();
    private final List<Player> frozen_rotation = new ArrayList<Player>();
    
    public PlayerFreezer() {
        Bukkit.getPluginManager().registerEvents(this,
                PexelSlaveBukkitPlugin.getInstance());
    }
    
    /**
     * Freezes (disables movement) the player.
     * 
     * @param player
     *            player to freeze
     */
    public void freeze(final Player player) {
        this.freeze(player, false);
    }
    
    public void freeze(final Player player, final boolean rotation) {
        this.frozen_movement.add(player);
        if (rotation)
            this.frozen_rotation.add(player);
    }
    
    /**
     * Unfreezes (enables movement) the player.
     * 
     * @param player
     *            player to unfreeze
     */
    public void unfreeze(final Player player) {
        this.frozen_movement.remove(player);
        this.frozen_rotation.remove(player);
    }
    
    @EventHandler
    private void onPlayerMove(final PlayerMoveEvent event) {
        if (this.frozen_movement.contains(event.getPlayer()))
            event.setTo(event.getFrom());
    }
}
