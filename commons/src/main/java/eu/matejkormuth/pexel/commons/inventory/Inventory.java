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
package eu.matejkormuth.pexel.commons.inventory;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import eu.matejkormuth.pexel.commons.Material;
import eu.matejkormuth.pexel.commons.Player;

/**
 * Interface that represents Inventory in game. Defines how should each inventory behave.
 */
public interface Inventory {
    
    /**
     * Adds to inventory item specified by arguments. Fails if transaction cannot be completed without loosing any
     * items. Calling this has the same effect as calling {@link #add(Material, int, short, byte)}.
     * 
     * @param itemStack
     *            representation of items to add in {@link ItemStack}.
     * @throws RuntimeException
     *             when there is not enough space for this transaction
     */
    void add(@Nonnull ItemStack itemStack);
    
    /**
     * Adds to inventory item specified by arguments. Fails if transaction cannot be completed without loosing any
     * items. Simmilar as calling {@link #add(Material, int, short, byte)}, but with durability and data set at zero
     * (default value).
     * 
     * @param material
     *            material of item to add
     * @param amount
     *            amount of item to add
     * @throws RuntimeException
     *             when there is not enough space for this transaction
     */
    void add(Material material, int amount);
    
    /**
     * Adds to inventory item specified by arguments. Fails if transaction cannot be completed without loosing any
     * items.
     * 
     * @param material
     *            material of item to add
     * @param amount
     *            amount of item to add
     * @param durability
     *            durability of item
     * @param data
     *            data value of item
     * @throws RuntimeException
     *             when there is not enough space for this transaction
     */
    void add(Material material, int amount, short durability, byte data);
    
    /**
     * Takes item(s) from inventory using {@link ItemStack} as source of data to take. Has same effect as calling
     * {@link #add(Material, int, short, byte)}.
     * 
     * @param itemStack
     *            itemStack to add
     */
    void take(@Nonnull ItemStack itemStack);
    
    /**
     * Takes item from inventory specified by arguments. Fails if item with specified durability and data is not found
     * in inventory.
     * 
     * @param material
     *            material of item to add
     * @param amount
     *            amount of item to add
     * @param durability
     *            durability of item
     * @param data
     *            data value of item
     * @throws RuntimeException
     *             when item with specified material durability and data doesnt exists in inventory
     */
    void take(Material material, int amount, short durability, byte data);
    
    /**
     * Takes all items with specified material from inventory regardless thier amount, durability or data value..
     * 
     * @param material
     *            material to remove from inventory
     */
    void takeAll(Material material);
    
    /**
     * Returns whether this inventory contains item(s) with specified material.
     * 
     * @param material
     *            material to look for
     * @return true if this inventory has at least one item with specified material, false otherwise
     */
    boolean contains(Material material);
    
    /**
     * Returns number of slot of first occurance of specified material in inventory. If inventory does not contain any
     * of specified material, function returns -1.
     * 
     * @param material
     *            material to get first slot of
     * @return id of first slot containing specified material or -1 when inventory does not contain specified material
     */
    int firstOccurance(Material material);
    
    /**
     * Returns contents of specified inventory slot. Returns null if slot is empty.
     * 
     * @param slot
     *            slot to get content from
     * @return {@link ItemStack} if slot contains item stack, null otherwise
     */
    ItemStack getSlotContents(int slot);
    
    /**
     * Sets content ({@link ItemStack}) of specified inventory slot.
     * 
     * @param slot
     *            slot to set content
     * @param itemStack
     *            content to be set
     */
    void setSlotContents(int slot, @Nullable ItemStack itemStack);
    
    /**
     * Removes all items from inventory.
     */
    void clear();
    
    /**
     * Returns whether the inventory is empty (contains no items).
     * 
     * @return true if inventory is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Checks for whether this Inventory currently has viewers.
     * 
     * @return true if viewers are currently looking at this inventory
     */
    boolean hasViewers();
    
    /**
     * Gets the current viewers looking at this Inventory.
     * 
     * @return the current viewers of this inventory
     */
    Set<Player> getViewers();
    
    /**
     * Returns inventory title.
     * 
     * @return title of this inventory
     */
    String getTitle();
    
    /**
     * Sets title of this inventory.
     * 
     * @param title
     *            new title for this inventory
     */
    void setTitle(@Nonnull String title);
    
}
