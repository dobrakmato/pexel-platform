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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import eu.matejkormuth.pexel.commons.annotations.JsonType;

/**
 * Class that represents revision.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@JsonType
public class Revision implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Higher revision will be evaluated as newer version.
     */
    protected int             revision;
    /**
     * String representation of this revision.
     */
    protected String          name;
    
    public Revision() {
    }
    
    /**
     * Creates new Revision with specified numeric revision and name.
     * 
     * @param revision
     *            numeric representaton of this revision
     * @param name
     *            human compactibile name of this revision
     */
    public Revision(final int revision, final String name) {
        this.revision = revision;
        this.name = name;
    }
    
    /**
     * Returns numeric representation of this revision.
     * 
     * @return numeric representation
     */
    public int getNumeric() {
        return this.revision;
    }
    
    /**
     * Set's numeric representation of this revision.
     * 
     * @param revision
     *            numeric representation
     */
    public void setNumeric(final int revision) {
        this.revision = revision;
    }
    
    /**
     * Returns human readable name of this revision / version.
     * 
     * @return human readable string
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Set's human readable name of this revision / version.
     * 
     * @param name
     *            new human readable name
     */
    public void setName(final String name) {
        this.name = name;
    }
    
}
