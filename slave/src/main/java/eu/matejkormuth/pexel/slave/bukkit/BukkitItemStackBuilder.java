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
package eu.matejkormuth.pexel.slave.bukkit;

import java.util.List;

import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.pexel.commons.ItemStack;
import eu.matejkormuth.pexel.commons.ItemStackBuilder;
import eu.matejkormuth.pexel.commons.Material;

/**
 * Bukkit item stack builder.
 */
public class BukkitItemStackBuilder implements ItemStackBuilder {
    private final org.bukkit.inventory.ItemStack itemstack;
    
    public BukkitItemStackBuilder(final Material material) {
        this.itemstack = new org.bukkit.inventory.ItemStack(
                org.bukkit.Material.valueOf(material.name()), 1);
    }
    
    @Override
    public BukkitItemStackBuilder damage(final short durability) {
        this.itemstack.setDurability(durability);
        return this;
    }
    
    @Override
    public BukkitItemStackBuilder quantity(final int amount) {
        this.itemstack.setAmount(amount);
        return this;
    }
    
    @Override
    public ItemStack build() {
        return null;
    }
    
    @Override
    public BukkitItemStackBuilder name(final String name) {
        ItemMeta meta = this.itemstack.getItemMeta();
        meta.setDisplayName(name);
        this.itemstack.setItemMeta(meta);
        return this;
    }
    
    @Override
    public BukkitItemStackBuilder lore(final List<String> lines) {
        ItemMeta meta = this.itemstack.getItemMeta();
        meta.setLore(lines);
        this.itemstack.setItemMeta(meta);
        return this;
    }
    
    @Override
    public BukkitItemStackBuilder lore(final int line, final String content) {
        ItemMeta meta = this.itemstack.getItemMeta();
        if (meta.getLore() != null) {
            List<String> lines = meta.getLore();
            if (lines.get(line) != null) {
                lines.set(line, content);
            }
        }
        this.itemstack.setItemMeta(meta);
        return this;
    }
    
}
