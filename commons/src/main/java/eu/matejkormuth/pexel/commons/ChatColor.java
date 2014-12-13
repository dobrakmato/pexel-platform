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

/**
 * Class that represents chat color code.
 */
public class ChatColor {
    public static final ChatColor BLACK        = new ChatColor('0');
    public static final ChatColor DARK_BLUE    = new ChatColor('1');
    public static final ChatColor DARK_GREEN   = new ChatColor('2');
    public static final ChatColor DARK_AQUA    = new ChatColor('3');
    public static final ChatColor DARK_RED     = new ChatColor('4');
    public static final ChatColor DARK_PURPLE  = new ChatColor('5');
    public static final ChatColor GOLD         = new ChatColor('6');
    public static final ChatColor GRAY         = new ChatColor('7');
    public static final ChatColor DARK_GRAY    = new ChatColor('8');
    public static final ChatColor BLUE         = new ChatColor('9');
    public static final ChatColor GREEN        = new ChatColor('a');
    public static final ChatColor AQUA         = new ChatColor('b');
    public static final ChatColor RED          = new ChatColor('c');
    public static final ChatColor LIGHT_PURPLE = new ChatColor('d');
    public static final ChatColor YELLOW       = new ChatColor('e');
    public static final ChatColor WHITE        = new ChatColor('f');
    
    public static final ChatColor RESET        = new ChatColor('r');
    
    public static final ChatColor OBFUSCATED   = new ChatColor('k');
    public static final ChatColor BOLD         = new ChatColor('l');
    public static final ChatColor STRIKETROUGH = new ChatColor('m');
    public static final ChatColor UNDERLINE    = new ChatColor('n');
    public static final ChatColor ITALIC       = new ChatColor('o');
    
    private final char            code;
    
    private ChatColor(final char code) {
        this.code = code;
    }
    
    /**
     * Returns character used by this chat color code.
     * 
     * @return character of this color code
     */
    public char getChar() {
        return this.code;
    }
    
    @Override
    public String toString() {
        return "&" + this.code;
    }
}
