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
package commons;

import org.junit.Test;

import eu.matejkormuth.pexel.commons.TextTable;

/**
 * {@link TextTable} test.
 */
public class TableTest {
    
    @Test
    public void test() {
        TextTable t = new TextTable(50, 6);
        t.renderCenteredText(1, "Party Invitation");
        t.renderSpacerLine(2);
        t.renderCenteredText(3, "Player pitkes22 invites You to his party!");
        t.renderCenteredText(4, "Type /party acccept to join!");
        t.formatPart(1, "Party Invitation", "&n");
        t.formatPart(3, "pitkes22", "&e");
        t.formatBorder("&a");
        //System.out.println(t.toString());
        
        t = new TextTable(50, 8);
        t.renderCenteredText(1, "Match summary");
        t.renderSpacerLine(2);
        t.renderCenteredText(3, "You went pretty well!");
        t.renderLeftAlignedText(4, "Killed animals:");
        t.renderRightAlignedText(4, "28");
        t.renderLeftAlignedText(5, "Killed mobs:");
        t.renderRightAlignedText(5, "145");
        t.renderLeftAlignedText(6, "Killed players:");
        t.renderRightAlignedText(6, "12");
        //System.out.println(t.toString());
    }
    
}
