package eu.matejkormuth.pexel.slave.bukkit.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.commons.minigame.Minigame;

/**
 * Represents achievements over network.
 */
public class Achievements {
    private final Map<String, Achievement> achievements = new HashMap<String, Achievement>();
    
    /**
     * Registers achievement in pexel to enable its synchronization over network.
     * 
     * @see Achievement#minigame(Minigame, String, String)
     * @see Achievement#global(String, String)
     * 
     * @param achievement
     *            achievement to register.
     */
    public void registerAchievement(final Achievement achievement) {
        Log.info("Registering achievement '" + achievement.getDisplayName() + "'...");
        this.achievements.put(achievement.getName(), achievement);
    }
    
    /**
     * Returns achievement by name or null if achievement for specified name not found.
     * 
     * @param name
     *            achievement name
     * @return achievement object or null if not found
     */
    public Achievement getAchievement(final String name) {
        return this.achievements.get(name);
    }
    
    /**
     * Set's progress to specified value.
     * 
     * @param progress
     *            new progress
     * @throws IllegalArgumentException
     *             when progress is bigger the max progress
     */
    public void setProgress(final Achievement achievement, final Player player,
            final int progress) {
        if (progress <= achievement.getMaxProgress()) {
            this.update(achievement, player, progress);
        }
        else {
            throw new IllegalArgumentException("progress is smalled the maxProgress");
        }
    }
    
    /**
     * Returns the current progress of achievement.
     * 
     * @return the current progress
     */
    public int getProgress(final Achievement achievement, final Player player) {
        return this.getState(achievement, player);
    }
    
    /**
     * Sets player status of this achievement to 'achieved' (achievement progress to max progress value -
     * {@link Achievement#getMaxProgress()}).
     * 
     * @param achievement
     *            achievement to unlock
     * @param player
     *            player
     */
    public void achieve(final Achievement achievement, final Player player) {
        this.update(achievement, player, achievement.getMaxProgress());
    }
    
    /**
     * Returns whether the specified player has achieved specififed achievement (progress equals to achievement's max
     * progress - {@link Achievement#getMaxProgress()}).
     * 
     * @param achievement
     *            achievement to check
     * @param player
     *            player to check
     * @return true or false
     */
    public boolean hasAchieved(final Achievement achievement, final Player player) {
        return this.getState(achievement, player) == achievement.getMaxProgress();
    }
    
    private int getState(final Achievement achievement, final Player player) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    private void update(final Achievement achievement, final Player player,
            final int maxProgress) {
        // TODO Auto-generated method stub
    }
}
