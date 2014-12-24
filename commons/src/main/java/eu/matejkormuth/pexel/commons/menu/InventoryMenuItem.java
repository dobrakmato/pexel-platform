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

import eu.matejkormuth.pexel.commons.actions.Action;
import eu.matejkormuth.pexel.commons.inventory.ItemStack;

/**
 * Interface that represents item in {@link InventoryMenu}.
 */
public interface InventoryMenuItem {
    /**
     * Returns {@link Action} that will be executed after player clicks on item.
     * 
     * @return this item's click {@link Action}
     */
    Action getClickAction();
    
    /**
     * Returns {@link ItemStack} that is used as icon of this menu item.
     * 
     * @return {@link ItemStack} of this menu item
     */
    ItemStack getItemStack();
}
