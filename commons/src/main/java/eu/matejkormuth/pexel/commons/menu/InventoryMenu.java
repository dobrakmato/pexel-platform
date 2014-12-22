package eu.matejkormuth.pexel.commons.menu;

import eu.matejkormuth.pexel.commons.Player;

/**
 * Interface that represents inventory menu.
 * 
 * @see InventoryMenuItem
 */
public interface InventoryMenu {
    
    /**
     * Adds specified item to next free slot in this inventory menu.
     * 
     * @param item
     *            item to add
     */
    void addItem(InventoryMenuItem item);
    
    /**
     * Adds specified item to specified slot in this inventory menu. This method throws exception if is slot already
     * occupied. If you need to replace item at specified slot, please use {@link #setItem(int, InventoryMenuItem)}
     * method.
     * 
     * @param slot
     *            slot to add item at
     * @param item
     *            item to add
     * @throws RuntimeException
     *             if specified slot is already occupied by another item.
     */
    void addItem(int slot, InventoryMenuItem item);
    
    /**
     * Adds or replaces item at specified slot by new item.
     * 
     * @param slot
     *            slot to add item at
     * @param item
     *            item to add
     */
    void setItem(int slot, InventoryMenuItem item);
    
    /**
     * Returns {@link InventoryMenuItem} at specified slot or null if there isn't item at specified slot.
     * 
     * @param slot
     *            slot to get item from
     * @return {@link InventoryMenuItem} or null, if slot is empty
     */
    InventoryMenuItem getItem(int slot);
    
    /**
     * Opens this {@link InventoryMenu} to specified player as inventory view.
     * 
     * @param player
     *            player to open this inventory menu to
     */
    void openTo(Player player);
    
    /**
     * Returns inventory that is backing this inventory menu.
     * 
     * @return inventory that holds items in this menu
     */
    // Object will be replaced by Inventory type when available.
    Object getInventory();
}
