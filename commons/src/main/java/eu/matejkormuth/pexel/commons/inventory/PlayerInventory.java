package eu.matejkormuth.pexel.commons.inventory;

/**
 * Interface that represents player inventory.
 */
public interface PlayerInventory extends Inventory {
    /**
     * Returns {@link ItemStack} of helmet slot in this inventory.
     * 
     * @return {@link ItemStack} of helmet
     */
    public abstract ItemStack getHelmet();
    
    /**
     * Returns {@link ItemStack} of chestplate slot in this inventory.
     * 
     * @return {@link ItemStack} of chestplate
     */
    public abstract ItemStack getChestplate();
    
    /**
     * Returns {@link ItemStack} of leggins slot in this inventory.
     * 
     * @return {@link ItemStack} of leggins
     */
    public abstract ItemStack getLeggins();
    
    /**
     * Returns {@link ItemStack} of boots slot in this inventory.
     * 
     * @return {@link ItemStack} of boots
     */
    public abstract ItemStack getBoots();
    
    /**
     * Sets {@link ItemStack} to helmet slot of this inventory.
     * 
     * @param helmet
     *            new item for helmet slot
     */
    public abstract void setHelmet(ItemStack helmet);
    
    /**
     * Sets {@link ItemStack} to chestplate slot of this inventory.
     * 
     * @param chestplate
     *            new item for chestplate slot
     */
    public abstract void setChestplate(ItemStack chestplate);
    
    /**
     * Sets {@link ItemStack} to leggins slot of this inventory.
     * 
     * @param leggins
     *            new item for leggins slot
     */
    public abstract void setLeggins(ItemStack leggins);
    
    /**
     * Sets {@link ItemStack} to boots slot of this inventory.
     * 
     * @param boots
     *            new item for boots slot
     */
    public abstract void setBoots(ItemStack boots);
}
