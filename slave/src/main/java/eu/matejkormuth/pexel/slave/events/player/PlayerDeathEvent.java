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
package eu.matejkormuth.pexel.slave.events.player;

import java.util.List;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.inventory.ItemStack;

/**
 * Fired when player entity dies.
 */
public class PlayerDeathEvent extends PlayerEvent {
    private List<ItemStack> drops;
    private int             xp_drops;
    
    public PlayerDeathEvent(final Player player) {
        super(player);
        // TODO Auto-generated constructor stub
    }
    
    public void setDroppedExp(final int i) {
        this.xp_drops = i;
    }
    
    public int getDroppedExp() {
        return this.xp_drops;
    }
    
    public List<ItemStack> getDrops() {
        return this.drops;
    }
}
