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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Class that represents revision.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Revision {
    /**
     * Higher revision will be evaluated as newer version.
     */
    protected int    revision;
    /**
     * String representation of this revision.
     */
    protected String name;
    
    public Revision() {
    }
    
    public Revision(final int revision, final String name) {
        this.revision = revision;
        this.name = name;
    }
    
    public int getNumeric() {
        return this.revision;
    }
    
    public void setRevision(final int revision) {
        this.revision = revision;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
}
