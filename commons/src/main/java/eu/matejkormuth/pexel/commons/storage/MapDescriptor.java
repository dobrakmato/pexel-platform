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
package eu.matejkormuth.pexel.commons.storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eu.matejkormuth.pexel.commons.JsonType;
import eu.matejkormuth.pexel.commons.MapSaveType;

/**
 * Description of map.
 */
@JsonType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MapDescriptor extends Description {
    private MapSaveType saveType;
    private String   minigame;
    
    /**
     * Only for JAXB.
     */
    public MapDescriptor() {
    }
    
    public MapSaveType getSaveType() {
        return this.saveType;
    }
    
    public void setSaveType(final MapSaveType saveType) {
        this.saveType = saveType;
    }
    
    public String getMinigame() {
        return this.minigame;
    }
    
    public void setMinigame(final String minigame) {
        this.minigame = minigame;
    }
}
