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

import com.google.common.base.Preconditions;

import eu.matejkormuth.pexel.commons.Material;
import eu.matejkormuth.pexel.commons.Player;

/**
 * Class that represents inventory.
 */
public class InventoryImpl implements Inventory {
    private final ItemStack[] slots;
    private String            title;
    
    public InventoryImpl(final int size) {
        this.slots = new ItemStack[size];
    }
    
    public InventoryImpl(final int size, final String title) {
        this.slots = new ItemStack[size];
    }
    
    public int addItem(@Nonnull final ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack);
        Preconditions.checkArgument(itemStack.getAmount() != 0,
                "Can't add ItemStack with zero amount!");
        
        ItemStack slot = null;
        for (int i = 0; i < this.slots.length; i++) {
            slot = this.slots[i];
            if (slot != null && slot.getMaterial().equals(itemStack.getMaterial())) {
                if (slot.getAmount() < slot.getMaterial().getMaxStackSize()) {
                    if (slot.getData() == itemStack.getData()
                            && slot.getDurability() == itemStack.getDurability()) {
                        // Allow merge.
                        
                    }
                }
            }
        }
        throw new RuntimeException("No more space in this inventory!");
    }
    
    @Override
    public int firstOccurance(final Material material) {
        Preconditions.checkNotNull(material);
        
        for (int i = 0; i < this.slots.length; i++) {
            if (this.slots[i] != null && this.slots[i].getMaterial().equals(material)) { return i; }
        }
        return -1;
    }
    
    @Override
    public ItemStack getSlotContents(final int slot) {
        return this.slots[slot];
    }
    
    @Override
    public void setSlotContents(final int slot, @Nullable final ItemStack itemStack) {
        this.slots[slot] = itemStack;
    }
    
    @Override
    public void clear() {
        for (int i = 0; i < this.slots.length; i++) {
            this.slots[i] = null;
        }
    }
    
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.slots.length; i++) {
            if (this.slots[i] != null) { return false; }
        }
        return true;
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }
    
    @Override
    public void add(@Nonnull final ItemStack itemStack) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void add(final Material material, final int amount) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void add(final Material material, final int amount, final short durability,
            final byte data) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void take(@Nonnull final ItemStack itemStack) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void take(final Material material, final int amount, final short durability,
            final byte data) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void takeAll(final Material material) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean contains(final Material material) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean hasViewers() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public Set<Player> getViewers() {
        // TODO Auto-generated method stub
        return null;
    }
}
