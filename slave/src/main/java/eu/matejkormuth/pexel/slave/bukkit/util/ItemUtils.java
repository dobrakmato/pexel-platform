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

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

/**
 * Class that contains many useful functions for working with InventoryMenus.
 * 
 * @author Mato Kormuth
 * 
 */
public class ItemUtils {
    public static ItemStack itemStack(final Material material, final int amount,
            final byte data, final short damage, final String displayName,
            final List<String> lore) {
        @SuppressWarnings("deprecation") ItemStack is = new ItemStack(material, amount,
                damage, data);
        ItemMeta im = is.getItemMeta();
        if (im != null)
            im.setDisplayName(displayName);
        if (lore != null)
            im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }
    
    public static ItemStack itemStack(final Material material, final int amount,
            final String displayName, final List<String> lore) {
        ItemStack is = new ItemStack(material, amount);
        ItemMeta im = is.getItemMeta();
        if (im != null)
            im.setDisplayName(displayName);
        if (lore != null)
            im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }
    
    /**
     * Just a utility, that returns names item stack with amount of 1.
     * 
     * <code><br><br>
     * ItemStack is = new ItemStack(material);<br>
     * 	ItemMeta im = is.getItemMeta();<br>
     * 	if (im != null)<br>
     * 		im.setDisplayName(displayName);<br>
     * 	if (lore != null)<br>
     * 		im.setLore(lore);<br>
     * 	is.setItemMeta(im);<br>
     * 	return is;<br>
     * </code>
     * 
     * @param material
     * @param displayName
     * @param lore
     * @return
     */
    public static ItemStack namedItemStack(final Material material,
            final String displayName, final List<String> lore) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        if (im != null)
            im.setDisplayName(displayName);
        if (lore != null)
            im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }
    
    /**
     * Returns specified colored lether armor.
     * 
     * @param material
     * @param color
     * @return
     */
    public static ItemStack coloredLetherArmor(final Material material, final Color color) {
        ItemStack larmor = new ItemStack(material, 1);
        LeatherArmorMeta lam = (LeatherArmorMeta) larmor.getItemMeta();
        lam.setColor(color);
        larmor.setItemMeta(lam);
        return larmor;
    }
    
    public static ItemStack skull(final String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        skull.setItemMeta(meta);
        return skull;
    }
    
    public static Builder builder(final Material material) {
        return new ItemUtils.Builder(material);
    }
    
    public static class Builder {
        private final ItemStack is;
        
        public Builder(final Material material) {
            this.is = new ItemStack(material);
        }
        
        public ItemStack build() {
            return this.is;
        }
        
        public Builder amount(final int amount) {
            this.is.setAmount(amount);
            return this;
        }
        
        public Builder durability(final short durability) {
            this.is.setDurability(durability);
            return this;
        }
        
        public Builder data(final MaterialData data) {
            this.is.setData(data);
            return this;
        }
        
        public Builder enchant(final Enchantment enchantment, final int level) {
            this.is.addEnchantment(enchantment, level);
            return this;
        }
        
        public Builder unsafeEnchant(final Enchantment enchantment, final int level) {
            this.is.addUnsafeEnchantment(enchantment, level);
            return this;
        }
        
        public Builder lore(final List<String> lore) {
            ItemMeta im = this.is.getItemMeta();
            im.setLore(lore);
            this.is.setItemMeta(im);
            return this;
        }
        
        public Builder name(final String displayName) {
            ItemMeta im = this.is.getItemMeta();
            im.setDisplayName(displayName);
            this.is.setItemMeta(im);
            return this;
        }
    }
    
    /**
     * Returns display name or null.
     * 
     * @param weapon
     * @return display name or null
     */
    public static String getDisplayName(final ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        return meta.getDisplayName();
    }
    
    /**
     * @param weapon
     * @param laserPistol
     * @return
     */
    public static boolean sameName(final ItemStack first, final ItemStack second) {
        return ItemUtils.getDisplayName(first).equals(ItemUtils.getDisplayName(second));
    }
}
