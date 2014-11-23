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
package eu.matejkormuth.pexel.slave.bukkit.kits;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Class used for applying kits on players.
 */
public class Kit {
    private final List<ItemStack> items;
    private ItemStack             helmet;
    private ItemStack             chestplate;
    private ItemStack             leggings;
    private ItemStack             boots;
    private final ItemStack       icon;
    
    /**
     * Creates a new kit with specified items.
     * 
     * @param items
     *            items in inventory
     */
    public Kit(final List<ItemStack> items, final ItemStack icon) {
        this.items = items;
        this.icon = icon;
    }
    
    /**
     * Creates a new kit with specified armor and items.
     * 
     * @param items
     *            items in inventory
     * @param helmet
     * @param chestplate
     * @param leggings
     * @param boots
     */
    public Kit(final List<ItemStack> items, final ItemStack icon,
            final ItemStack helmet, final ItemStack chestplate,
            final ItemStack leggings, final ItemStack boots) {
        this.items = items;
        this.icon = icon;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }
    
    /**
     * Applies kit on specified player, removing all previous items.
     * 
     * @param player
     *            player to give kit to
     */
    public void applyKit(final Player player) {
        player.getInventory().clear();
        
        //TODO: DEBUKKITIZE:
        //FIX: If kit contains no armor, the armor from previous kit would be preserved.
        //player.getInventory().setHelmet(null);
        //player.getInventory().setChestplate(null);
        //player.getInventory().setLeggings(null);
        //player.getInventory().setBoots(null);
        
        //if (this.helmet != null)
        //player.getInventory().setHelmet(this.helmet);
        //if (this.chestplate != null)
        //player.getInventory().setChestplate(this.chestplate);
        //if (this.leggings != null)
        //player.getInventory().setLeggings(this.leggings);
        //if (this.boots != null)
        //player.getInventory().setBoots(this.boots);
        
        //for (ItemStack itemstack : this.items)
        //    player.getInventory().addItem(itemstack);
    }
    
    /**
     * Returns item stack of this kit icon.
     * 
     * @return item stack representing icon
     */
    public ItemStack getIcon() {
        return this.icon;
    }
}
