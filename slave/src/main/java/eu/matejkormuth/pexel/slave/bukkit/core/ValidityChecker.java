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
