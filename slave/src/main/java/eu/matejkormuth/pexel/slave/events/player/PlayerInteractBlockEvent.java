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

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.inventory.ItemStack;
import eu.matejkormuth.pexel.commons.math.Vector3d;
import eu.matejkormuth.pexel.slave.modules.MouseButton;

/**
 *
 */
public class PlayerInteractBlockEvent extends PlayerEvent {
    private final Vector3d    blockPos;
    private final ItemStack   itemInHand;
    private final MouseButton usedButton;
    
    public PlayerInteractBlockEvent(final Player player, final Vector3d block,
            final ItemStack itemInHand, final MouseButton button) {
        super(player);
        this.blockPos = block.floor();
        this.itemInHand = itemInHand;
        this.usedButton = button;
    }
    
    public Vector3d getBlockPos() {
        return this.blockPos;
    }
    
    public ItemStack getItemInHand() {
        return this.itemInHand;
    }
    
    public MouseButton getUsedButton() {
        return this.usedButton;
    }
}
