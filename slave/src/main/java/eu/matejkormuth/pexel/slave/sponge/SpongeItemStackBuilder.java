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
package eu.matejkormuth.pexel.slave.sponge;

import java.util.List;

import eu.matejkormuth.pexel.commons.Material;
import eu.matejkormuth.pexel.commons.inventory.ItemStack;
import eu.matejkormuth.pexel.commons.inventory.ItemStackBuilder;

/**
 * Sponge item stack builder.
 */
public class SpongeItemStackBuilder implements ItemStackBuilder {
    org.spongepowered.api.item.inventory.ItemStack itemstack;
    
    public SpongeItemStackBuilder(final Material material) {
        this.itemstack = null;
    }
    
    @Override
    public SpongeItemStackBuilder damage(final short durability) {
        this.itemstack.setDamage(durability);
        return this;
    }
    
    @Override
    public SpongeItemStackBuilder quantity(final int amount) {
        this.itemstack.setQuantity(amount);
        return this;
    }
    
    @Override
    public ItemStack build() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public SpongeItemStackBuilder name(final String name) {
        return this;
    }
    
    @Override
    public SpongeItemStackBuilder lore(final List<String> lines) {
        // TODO Auto-generated method stub
        return this;
        
    }
    
    @Override
    public SpongeItemStackBuilder lore(final int line, final String content) {
        // TODO Auto-generated method stub
        return this;
    }
    
}
