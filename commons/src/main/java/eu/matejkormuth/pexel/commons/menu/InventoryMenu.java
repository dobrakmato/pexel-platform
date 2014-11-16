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
package eu.matejkormuth.pexel.commons.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Class used for Inventory Menu.
 * 
 * @author Mato Kormuth
 * 
 */
public class InventoryMenu implements InventoryHolder {
    /**
     * Inventory of this menu.
     */
    private final Inventory                       inventory;
    /**
     * Items in inventory.
     */
    private final Map<Integer, InventoryMenuItem> items = new HashMap<Integer, InventoryMenuItem>();
    
    /**
     * Creates new Inventory menu object with specified type of inventory, title and list of items.
     * 
     * @see InventoryMenuItem
     * @see Action
     * @see ItemUtils
     * 
     * @param type
     *            type of inventory
     * @param title
     *            title of inventory
     * @param items
     *            items
     */
    public InventoryMenu(final InventoryType type, final String title,
            final List<InventoryMenuItem> items) {
        for (InventoryMenuItem item : items)
            if (!this.items.containsKey(item.getSlot()))
                this.items.put(item.getSlot(), item);
            else
                throw new RuntimeException("Can't put " + item.getItemStack().toString()
                        + " to slot " + item.getSlot() + "! Slot " + item.getSlot()
                        + " is alredy occupied by "
                        + this.items.get(item.getSlot()).getItemStack().toString());
        
        this.inventory = Bukkit.createInventory(this, type, title);
        for (InventoryMenuItem item : this.items.values())
            this.inventory.setItem(item.getSlot(), item.getItemStack());
    }
    
    /**
     * 
     * Creates new Inventory menu object with specified size of inventory, title and list of items.
     * 
     * @param size
     *            size of inventory
     * @param title
     *            inventory title
     * @param items
     *            items
     */
    public InventoryMenu(final int size, final String title,
            final List<InventoryMenuItem> items) {
        
        for (InventoryMenuItem item : items)
            if (!this.items.containsKey(item.getSlot())) {
                this.items.put(item.getSlot(), item);
            }
            else {
                throw new RuntimeException("Can't put " + item.getItemStack().toString()
                        + " to slot " + item.getSlot() + "! Slot " + item.getSlot()
                        + " is alredy occupied by "
                        + this.items.get(item.getSlot()).getItemStack().toString());
            }
        
        this.inventory = Bukkit.createInventory(this, size, title);
        for (InventoryMenuItem item : this.items.values()) {
            this.inventory.setItem(item.getSlot(), item.getItemStack());
        }
    }
    
    /**
     * Opens this inventory menu to specified player.
     * 
     * @param player
     *            player to show menu to
     */
    public void showTo(final Player player) {
        player.openInventory(this.getInventory());
    }
    
    /**
     * Same as calling {@link InventoryMenu#showTo(Player)}.
     * 
     * @see InventoryMenu#showTo(Player)
     * 
     * @param player
     *            player to show menu to
     */
    public void openInventory(final Player player) {
        player.openInventory(this.getInventory());
    }
    
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
    
    /**
     * Returns true, if the menu should be closed after the item in specified slot is clicked.
     * 
     * @param slot
     *            slot to check
     * @return whether the inventory should close after click
     */
    public boolean shouldClose(final int slot) {
        return this.items.get(slot).isCloseAfterClick();
    }
    
    /**
     * Called when somebody clicks item in this inventory.
     * 
     * @param player
     * @param item
     */
    public void inventoryClick(final Player player, final int slot) {
        if (this.items.containsKey(slot)) {
            this.items.get(slot).executeAction(player);
        }
        else {
            //.warn("Player '" + player + "' clicked on invalid item at slot '" + slot
            //        + "' in inventoryMenu!");
        }
    }
}
