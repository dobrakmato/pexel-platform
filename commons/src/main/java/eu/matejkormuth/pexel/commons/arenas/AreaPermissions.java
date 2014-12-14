package eu.matejkormuth.pexel.commons.arenas;

import eu.matejkormuth.pexel.commons.permissions.Permission;

/**
 * List of permission associated with {@link ProtectedArea}.
 */
public class AreaPermissions {
    private AreaPermissions() {
    }
    
    /**
     * Block break event.
     */
    public static final Permission BLOCK_BREAK                = new Permission(
                                                                      "arena.block.break");
    /**
     * Block place event.
     */
    public static final Permission BLOCK_PLACE                = new Permission(
                                                                      "arena.block.place");
    /**
     * Player doing damage.
     */
    public static final Permission PLAYER_DODAMAGE            = new Permission(
                                                                      "arena.player.damage.do");
    /**
     * Player dropping item/s.
     */
    public static final Permission PLAYER_DROPITEM            = new Permission(
                                                                      "arena.player.dropitem");
    /**
     * Player getting damage.
     */
    public static final Permission PLAYER_GETDAMAGE           = new Permission(
                                                                      "arena.player.damage.get");
    /**
     * Starvation of player.
     */
    public static final Permission PLAYER_STARVATION          = new Permission(
                                                                      "arena.player.starvation");
    /**
     * Permission for receiving "Permission denied" message.
     */
    public static final Permission AREA_CHAT_PERMISSIONDENIED = new Permission(
                                                                      "arena.chat.permissiondenied");
    /**
     * Permission for receiving area welcome message.
     */
    public static final Permission AREA_CHAT_WELCOME          = new Permission(
                                                                      "arena.chat.welcome");
    /**
     * Permission for receiving arena goodbye message.
     */
    public static final Permission AREA_CHAT_GOODBYE          = new Permission(
                                                                      "arena.chat.goodbye");
}
