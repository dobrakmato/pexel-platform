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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Class that represents text table in minecraft chat. Supports redering borders, rendering text aligned to center,
 * right, left and formatting. Do rendering first, then do formatting.
 * </p>
 * 
 * <p>
 * Example usage will be:
 * 
 * <pre>
 * TextTable t = new TextTable(50, 6);
 * t.renderCenteredText(1, &quot;Party Invitation&quot;);
 * t.renderSpacerLine(2);
 * t.renderCenteredText(3, &quot;Player XYZ invites You to his party!&quot;);
 * t.renderCenteredText(4, &quot;Type /party acccept to join!&quot;);
 * </pre>
 * 
 * where {@link #toString()} method generates this table:
 * 
 * <pre>
 * ╔════════════════════════════════════════════════╗
 * ║               Party Invitation                 ║
 * ╠════════════════════════════════════════════════║
 * ║      Player XYZ invites You to his party!      ║
 * ║         Type /party acccept to join!           ║
 * ╚════════════════════════════════════════════════╝
 * </pre>
 * 
 * With formatting the usage will be (<b>don't forget to apply formatting AFTER you done all text rendering and line
 * manipulation!</b>):
 * 
 * <pre>
 * TextTable t = new TextTable(50, 6);
 * t.renderCenteredText(1, &quot;Party Invitation&quot;);
 * t.renderSpacerLine(2);
 * t.renderCenteredText(3, &quot;Player pitkes22 invites You to his party!&quot;);
 * t.renderCenteredText(4, &quot;Type /party acccept to join!&quot;);
 * t.formatPart(1, &quot;Party Invitation&quot;, &quot;&amp;n&quot;);
 * t.formatPart(3, &quot;pitkes22&quot;, &quot;&amp;e&quot;);
 * t.formatBorder(&quot;&amp;a&quot;);
 * </pre>
 * 
 * which's {@link #toString()} produces:
 * 
 * <pre>
 * &a╔════════════════════════════════════════════════╗
 * &a║&r               &nParty Invitation&r                 &a║&r
 * &a╠════════════════════════════════════════════════║
 * &a║&r   Player &epitkes22&r invites You to his party!    &a║&r
 * &a║&r         Type /party acccept to join!           &a║&r
 * &a╚════════════════════════════════════════════════╝
 * </pre>
 * 
 * which will be rendered in-game as:
 * 
 * <pre>
 * <span style="color:lime">╔════════════════════════════════════════════════╗</span>
 * <span style="color:lime">║</span>               <b>Party Invitation</b>                 <span style="color:lime">║</span>
 * <span style="color:lime">╠════════════════════════════════════════════════║</span>
 * <span style="color:lime">║</span>   Player <span style="color:gold">pitkes22</span> invites You to his party!    <span style="color:lime">║</span>
 * <span style="color:lime">║</span>         Type /party acccept to join!           <span style="color:lime">║</span>
 * <span style="color:lime">╚════════════════════════════════════════════════╝</span>
 * </pre>
 * 
 * </p>
 * 
 * @see #renderCenteredText(int, String)
 * @see #renderLeftAlignedText(int, String)
 * @see #renderRightAlignedText(int, String)
 * @see #renderSpacerLine(int)
 * @see #renderClearLine(int)
 * @see #insertNewLineBeforeLast()
 * @see #formatBorder(String)
 * @see #formatLine(int, String)
 * @see #formatString(int, String, String)
 * @author Matej Kormuth
 */
public class TextTable {
    // Default minecraft chat width
    public static final int    MINECRAFT_CHAT_WIDTH = 35;
    // TextTable border and space characters.
    public static final String VERTICAL_SIDE        = "║";
    public static final String HORIZONTAL_SIDE      = "═";
    public static final String TOP_LEFT             = "╔";
    public static final String TOP_RIGHT            = "╗";
    public static final String BOTTOM_LEFT          = "╚";
    public static final String BOTTOM_RIGHT         = "╝";
    public static final String SPACE                = " ";
    // Reset style chat code.
    public static final String RESET_STYLE          = "&r";
    
    /**
     * Array of internal lines.
     */
    private StringBuilder[]    lines;
    private final int          internalWidth;
    
    /**
     * Constructs new TextTable with specified width and height. Height can be expanded later.
     * 
     * @param width
     *            width of table including border columns
     * @param height
     *            height of table including border rows
     */
    public TextTable(final int width, final int height) {
        this.internalWidth = width - 2;
        this.lines = new StringBuilder[height];
        for (int i = 0; i < this.lines.length; i++) {
            StringBuilder builder = new StringBuilder(width);
            if (i == 0) {
                builder.append(TOP_LEFT);
                for (int k = 0; k < this.internalWidth; k++) {
                    builder.append(HORIZONTAL_SIDE);
                }
                builder.append(TOP_RIGHT);
            }
            else if (i == this.lines.length - 1) {
                builder.append(BOTTOM_LEFT);
                for (int k = 0; k < this.internalWidth; k++) {
                    builder.append(HORIZONTAL_SIDE);
                }
                builder.append(BOTTOM_RIGHT);
            }
            else {
                builder.append(VERTICAL_SIDE);
                for (int k = 0; k < this.internalWidth; k++) {
                    builder.append(SPACE);
                }
                builder.append(VERTICAL_SIDE);
            }
            this.lines[i] = builder;
        }
    }
    
    /**
     * Renders specified text on specified line and specified index. Index must be bigger then zero and smalled then
     * width of row. If is too long, it will be trunctated.
     * 
     * @param line
     *            line to insert content at
     * @param index
     *            index to insert content at
     * @param content
     *            content to insert
     */
    public void renderText(final int line, final int index, final String content) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        Preconditions.checkPositionIndex(index, this.internalWidth);
        if (content.length() > this.internalWidth - index) {
            this.lines[line].replace(
                    index,
                    index + content.length() - (this.internalWidth - index),
                    content.substring(0, content.length() - (this.internalWidth - index)));
        }
        else {
            this.lines[line].replace(index, index + content.length(), content);
        }
    }
    
    /**
     * Inserts specified content at specified line aligning content to center. If is content too long, it will be
     * tructated.
     * 
     * @param line
     *            line to insert at
     * @param content
     *            content to insert
     */
    public void renderCenteredText(final int line, final String content) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        Preconditions.checkArgument(content.length() < this.internalWidth);
        this.renderText(line, this.internalWidth / 2 - content.length() / 2, content);
    }
    
    /**
     * Inserts specified content at specified line aligning content to right. If is content too long, it will be
     * tructated.
     * 
     * @param line
     *            line to insert at
     * @param content
     *            content to insert
     */
    public void renderRightAlignedText(final int line, final String content) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        Preconditions.checkArgument(content.length() < this.internalWidth);
        this.renderText(line, this.internalWidth - content.length(), content);
    }
    
    /**
     * Inserts specified content at specified line aligning content to left. If is content too long, it will be
     * tructated. Same as calling <code>renderText(line, 2, content)</code>.
     * 
     * @param line
     *            line to insert at
     * @param content
     *            content to insert
     */
    public void renderLeftAlignedText(final int line, final String content) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        Preconditions.checkArgument(content.length() < this.internalWidth);
        this.renderText(line, 2, content);
    }
    
    /**
     * Renders horizontal spacer line on specified line.
     * 
     * @param line
     *            line to render at
     */
    public void renderSpacerLine(final int line) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        for (int i = 0; i < this.internalWidth + 1; i++) {
            if (i == 0) {
                this.renderText(line, i, "╠");
            }
            else if (i == this.internalWidth + 2) {
                this.renderText(line, i, "╣");
            }
            else {
                this.renderText(line, i, HORIZONTAL_SIDE);
            }
        }
    }
    
    /**
     * Renders clear line (line with side borders, full width) on specified line.
     * 
     * @param line
     *            line to render at
     */
    public void renderClearLine(final int line) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        for (int i = 0; i < this.internalWidth + 1; i++) {
            if (i == 0) {
                this.renderText(line, i, VERTICAL_SIDE);
            }
            else if (i == this.internalWidth + 2) {
                this.renderText(line, i, VERTICAL_SIDE);
            }
            else {
                this.renderText(line, i, SPACE);
            }
        }
    }
    
    /**
     * Inserts empty line with side borders before last (border) line.
     */
    public void insertNewLineBeforeLast() {
        StringBuilder[] newlines = new StringBuilder[this.lines.length + 1];
        for (int i = 0; i < this.lines.length; i++) {
            if (i == this.lines.length - 1) {
                newlines[i] = new StringBuilder();
                newlines[i + 2] = this.lines[i];
            }
            else {
                newlines[i] = this.lines[i];
            }
        }
        this.lines = newlines;
        this.renderClearLine(this.lines.length - 1);
    }
    
    /**
     * Inserts empty lines with side borders before last (border) line.
     */
    public void insertNewLinesBeforeLast(final int count) {
        Preconditions.checkArgument(count > 0, "count < 0");
        
        StringBuilder[] newlines = new StringBuilder[this.lines.length + count + 1];
        for (int i = 0; i < this.lines.length; i++) {
            if (i == this.lines.length - 1) {
                for (int k = 0; k <= count; k++) {
                    newlines[i + k] = new StringBuilder();
                }
                newlines[i + count + 1] = this.lines[i];
            }
            else {
                newlines[i] = this.lines[i];
            }
        }
        this.lines = newlines;
        for (int i = 0; i < count; i++) {
            this.renderClearLine(this.lines.length - i - 1);
        }
    }
    
    /**
     * Formats table border to specified style. Use after rendering all content!
     * 
     * @param style
     *            style to apply to border.
     */
    public void formatBorder(final String style) {
        for (int i = 0; i < this.lines.length; i++) {
            if (i == 0 || i == this.lines.length - 1) {
                this.formatLine(i, style);
            }
            else if (this.lines[i].toString().contains("╠")) {
                this.formatLine(i, style);
            }
            else {
                int index = this.lines[i].indexOf(VERTICAL_SIDE);
                this.formatPart(i, index, 1, style);
                
                int index2 = this.lines[i].indexOf(VERTICAL_SIDE, this.internalWidth / 2);
                this.formatPart(i, index2, 1, style);
            }
        }
    }
    
    public void formatBorder(final ChatColor color) {
        this.formatBorder(color.toString());
    }
    
    /**
     * Formats line to specified style. Use after rendering all content!
     * 
     * @param line
     *            line to apply style to
     * @param style
     *            style to apply to line
     */
    public void formatLine(final int line, final String style) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        this.lines[line].insert(0, style);
    }
    
    /**
     * Formats specified part of specified line to specified style.
     * 
     * @param line
     *            line to format
     * @param index
     *            index, where style should start
     * @param length
     *            length of styled content
     * @param style
     *            style to apply to region
     */
    public void formatPart(final int line, final int index, final int length,
            final String style) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        this.lines[line].insert(index, style);
        this.lines[line].insert(index + style.length() + length, RESET_STYLE);
    }
    
    /**
     * Applies specified style to specified text, if found in specified line.
     * 
     * @param line
     *            line to search for
     * @param formatted
     *            text to apply style to
     * @param style
     *            style to be applied
     */
    public void formatString(final int line, final String formatted, final String style) {
        Preconditions.checkPositionIndex(line, this.lines.length);
        int index = this.lines[line].indexOf(formatted);
        this.formatPart(line, index, formatted.length(), style);
    }
    
    /**
     * Returns collection containing content of all lines in this table.
     * 
     * @return collection of all line in this table
     */
    public List<String> getLines() {
        List<String> lines = new ArrayList<String>(this.lines.length);
        for (StringBuilder line : this.lines) {
            lines.add(line.toString());
        }
        return lines;
    }
    
    /**
     * Returns content of specified line.
     * 
     * @param index
     *            line to get content of
     * @return content of specified line
     */
    public String getLine(final int index) {
        Preconditions.checkPositionIndex(index, this.lines.length);
        return this.lines[index].toString();
    }
    
    /**
     * Returns all lines of this table joined by newline char eg. formatted table.
     */
    @Override
    public String toString() {
        StringBuilder table = new StringBuilder();
        for (StringBuilder builder : this.lines) {
            table.append(builder.toString() + "\n");
        }
        return table.toString();
    }
}
