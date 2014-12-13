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
package eu.matejkormuth.pexel.commons.minigame;

/**
 * Class that contains minigames categories.
 */
public abstract class MinigameCategories {
    /**
     * Aracade minigame (ex. Tnt Tag).
     */
    public static final MinigameCategory ARCADE     = new MinigameCategory() {
                                                        @Override
                                                        public String getName() {
                                                            return "Arcade";
                                                        }
                                                    };
    /**
     * Minigame that is about to survive (ex. Survival Games).
     */
    public static final MinigameCategory SURVIVAL   = new MinigameCategory() {
                                                        @Override
                                                        public String getName() {
                                                            return "Survival";
                                                        }
                                                    };
    /**
     * Mingiame that is ready for tournaments (ex. Kingdom Wars, Annihilation). Minigames in this category have enabled
     * negative points for leaving match before match ends.
     */
    public static final MinigameCategory TOURNAMENT = new MinigameCategory() {
                                                        @Override
                                                        public String getName() {
                                                            return "Tournament";
                                                        }
                                                    };
}
