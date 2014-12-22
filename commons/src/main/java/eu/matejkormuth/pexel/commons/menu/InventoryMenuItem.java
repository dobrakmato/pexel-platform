package eu.matejkormuth.pexel.commons.menu;

import eu.matejkormuth.pexel.commons.ItemStack;
import eu.matejkormuth.pexel.commons.actions.Action;

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
