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

import java.util.LinkedList;

/**
 * Component list is a linked list that maintains ordering system first, user second. So the system items are on top of
 * the list and user are ordered after system items part.
 */
public class ComponentList<T> extends LinkedList<T> {
    private static final long serialVersionUID = 1L;
    private int               systemCount      = 0;
    private int               userCount        = 0;
    
    /**
     * Adds item to list to system (first) part.
     * 
     * @param item
     *            item to add
     */
    public void addSystem(final T item) {
        this.add(this.systemCount, item);
        this.systemCount++;
    }
    
    /**
     * Adds item to list to user (second) part.
     * 
     * @param item
     *            item to add
     */
    public void addUser(final T item) {
        this.add(this.systemCount + this.userCount, item);
        this.userCount++;
    }
}
