package eu.matejkormuth.pexel.slave.bukkit.core;

//@formatter:off
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
//@formatter:on
import eu.matejkormuth.pexel.commons.minigame.Minigame;

/**
 * Class that represents achievement.
 */
public class Achievement {
    private String name;
    private String displayName;
    private String description;
    private int    maxProgress;
    
    /**
     * Private constructor. Please use static methods.
     */
    private Achievement() {
        
    }
    
    /**
     * Builds achievement for specified minigame with specified name and display name.
     * 
     * @param minigame
     *            minigame
     * @param name
     *            name of achievement (will be automatically prefixed with '{minigamename}.').
     * @param description
     *            description of achievement
     * @param displayName
     *            display name
     * @param maxProgress
     *            maximum progress of achievement
     * @return achievement
     */
    public static Achievement minigame(final Minigame minigame, final String name,
            final String description, final String displayName, final int maxProgress) {
        Achievement ach = new Achievement();
        ach.name = minigame.getName() + "." + name;
        ach.displayName = displayName;
        ach.description = description;
        ach.maxProgress = maxProgress;
        return ach;
    }
    
    /**
     * Builds global achievement that applies to whole network with specified name and display name.
     * 
     * @param name
     *            name (will be automatically prefixed with 'global.').
     * @param displayName
     *            display name
     * @param description
     *            description of achievement
     * @param maxProgress
     *            maximum progress of achievement
     * @return achievement
     */
    public static Achievement global(final String name, final String displayName,
            final String description, final int maxProgress) {
        Achievement ach = new Achievement();
        ach.name = "global." + name;
        ach.displayName = displayName;
        ach.description = description;
        ach.maxProgress = maxProgress;
        return ach;
    }
    
    /**
     * Returns the name of achievement.
     * 
     * @return the name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Returns the max progress value of this achievement (<b>value, when achievement is awarded to player<b>).
     * 
     * @return the max progress value
     */
    public int getMaxProgress() {
        return this.maxProgress;
    }
    
    /**
     * Returns the display name of achievement.
     * 
     * @return the display name of achievement
     */
    public String getDisplayName() {
        return this.displayName;
    }
}
