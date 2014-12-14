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
package eu.matejkormuth.pexel.commons;

import java.util.List;

/**
 * Interfacec that declares how should item stack builders behave.
 */
public interface ItemStackBuilder {
    /**
     * Applies durability to this item stack.
     * 
     * @param durability
     *            specified durability level
     */
    public void durability(short durability);
    
    /**
     * Specifies amount of items in this item stack.
     * 
     * @param amount
     *            amount
     */
    public void amount(int amount);
    
    /**
     * Returns built item stack.
     * 
     * @return item stack
     */
    public ItemStack build();
    
    /**
     * Applies custom display name to this item stack.
     * 
     * @param name
     *            custom display name
     */
    public void name(String name);
    
    /**
     * Applies lore to this item stack.
     * 
     * @param lines
     *            lines with lore
     */
    public void lore(List<String> lines);
    
    /**
     * Applies lore to specfied line with specified content.
     * 
     * @param line
     *            zero based number of line
     * @param content
     *            content of line
     */
    public void lore(int line, String content);
}
