package eu.matejkormuth.pexel.slave.bukkit.core;

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
