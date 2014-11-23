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
package eu.matejkormuth.pexel.slave.bukkit.menu;

import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.slave.bukkit.actions.Action;

/**
 * Class that represents item in inventory menu.
 */
public class InventoryMenuItem {
    /**
     * ItemStack of item.
     */
    private final ItemStack item;
    /**
     * Action to execute when item is clicked.
     */
    private final Action    action;
    /**
     * Slot of inventory.
     */
    private final int       slot;
    /**
     * Specifies if the InventoryMenu have to close, after click.
     */
    private final boolean   closeAfterClick;
    
    /**
     * Creates new Inventory menu item from specified params.
     * 
     * @param item
     *            itemstack to use as icon.
     * @param action
     *            action to execute when player clicks icon.
     * @param slot
     *            slot in inventory, where the itemstack should be.
     * @param closeAfterClick
     *            boolean, that specify, if the menu should close after click on this item.
     */
    public InventoryMenuItem(final ItemStack item, final Action action, final int slot,
            final boolean closeAfterClick) {
        this.item = item;
        this.action = action;
        this.slot = slot;
        this.closeAfterClick = closeAfterClick;
    }
    
    /**
     * Executes action with specified player(sender).
     * 
     * @param player
     *            player that is task executed
     */
    public void executeAction(final Player player) {
        this.action.execute(player);
    }
    
    /**
     * Returns bukkit compactibile ItemStack of this menu item.
     * 
     * @return item stack
     */
    public ItemStack getItemStack() {
        return this.item;
    }
    
    /**
     * Returns slot in minecraft inventory.
     * 
     * @return id of slot
     */
    public int getSlot() {
        return this.slot;
    }
    
    /**
     * Returns if the menu should close after click.
     */
    public boolean isCloseAfterClick() {
        return this.closeAfterClick;
    }
}
