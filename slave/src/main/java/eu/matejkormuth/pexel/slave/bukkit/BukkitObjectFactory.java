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

import java.util.UUID;

import org.bukkit.Bukkit;

import eu.matejkormuth.pexel.commons.AbstractObjectFactory;
import eu.matejkormuth.pexel.commons.ItemStackBuilder;
import eu.matejkormuth.pexel.commons.Material;
import eu.matejkormuth.pexel.commons.Player;

/**
 * Bukkit implementation of {@link AbstractObjectFactory}.
 */
public class BukkitObjectFactory extends AbstractObjectFactory {
    @Override
    public Player getPlayer(final UUID uuid) {
        org.bukkit.entity.Player p = Bukkit.getPlayer(uuid);
        if (p.isOnline()) {
            return new BukkitPlayer(p);
        }
        else {
            return null;
        }
    }
    
    @Override
    public Player getPlayer(final Object platformType) {
        if (platformType instanceof org.bukkit.entity.Player) {
            return new BukkitPlayer((org.bukkit.entity.Player) platformType);
        }
        else {
            throw new IllegalArgumentException(
                    "platformType must be type of org.bukkit.entity.Player");
        }
    }
    
    @Override
    public ItemStackBuilder createItemStackBuilder(final Material material) {
        return new BukkitItemStackBuilder(material);
    }
}
