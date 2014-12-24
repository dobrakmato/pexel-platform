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

import eu.matejkormuth.pexel.commons.Material;

/**
 * Class that represents an item stack.
 */
public class ItemStack {
    private final Material type;
    private short          durability;
    private int            amount;
    private byte           data;
    
    public ItemStack(final Material type) {
        this(type, 1);
    }
    
    public ItemStack(final Material type, final int amount) {
        this.type = type;
        this.amount = amount;
    }
    
    public short getDurability() {
        return this.durability;
    }
    
    public void setDurability(final short durability) {
        this.durability = durability;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public void setData(final byte data) {
        this.data = data;
    }
    
    public Material getMaterial() {
        return this.type;
    }
    
}
