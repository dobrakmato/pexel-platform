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
package eu.matejkormuth.pexel.slave.bukkit.core;

import org.apache.commons.lang.StringUtils;

import eu.matejkormuth.pexel.commons.minigame.Minigame;

/**
 * Class that is used for checking validity of parts registered to pexel.
 */
public final class ValidityChecker {
    public static void checkMinigame(final Minigame minigame) {
        if (!minigame.getName().matches("^[a-z0-9]*$")) { throw new ValidationException(
                "Minigame name does not match pattern '[a-zA-Z0-9]'!"); }
        if (minigame.getCategory() == null) { throw new ValidationException(
                "Minigame must return category!"); }
    }
    
    public static void checkMapData(final eu.matejkormuth.pexel.commons.MapData mapData) {
        if (StringUtils.isBlank(mapData.getAuthor())) { throw new ValidationException(
                "Map author can't be blank!"); }
        if (StringUtils.isBlank(mapData.getName())) { throw new ValidationException(
                "Map name can't be blank!"); }
        if (mapData.getMaxPlayers() < 1) {
            System.out.println("Max players should probably be more than one!");
        }
        if (mapData.getProtectedRegion() == null) { throw new ValidationException(
                "Protected region can't be null!"); }
        if (mapData.getWorld() == null) { throw new ValidationException(
                "World not found on server!"); }
    }
    
    public static class ValidationException extends RuntimeException {
        private static final long serialVersionUID = 8219849002256286968L;
        
        public ValidationException(final String s) {
            super(s);
        }
    }
}
