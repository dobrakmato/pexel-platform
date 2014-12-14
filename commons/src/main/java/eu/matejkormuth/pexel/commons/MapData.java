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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Class that represents data of playable map (not block data).
 */
@XmlType(name = "arenamap")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MapData {
    // Keys
    /**
     * Minimum number of players required to start arena.
     */
    public static transient final String      KEY_MINIMAL_PLAYERS  = "minimalplayers";
    /**
     * Length of countdown in seconds, when minimal amount of players joined arena.
     */
    public static transient final String      KEY_COUNTDOWN_LENGHT = "countdownlength";
    /**
     * Length of countdown in seconds, when minimal amount of players joined arena.
     */
    public static transient final String      KEY_ARENA_SPAWN      = "spawn";
    
    @XmlAttribute(name = "name")
    protected String                          name;
    @XmlAttribute(name = "minigameName")
    protected String                          minigameName;
    @XmlAttribute(name = "author")
    protected String                          author;
    
    @XmlElementWrapper(name = "options_text")
    protected final Map<String, String>       options_string       = new HashMap<String, String>();
    @XmlElementWrapper(name = "options_number")
    protected final Map<String, Integer>      options_int          = new HashMap<String, Integer>();
    @XmlElementWrapper(name = "locations")
    protected final Map<String, Location>     locations            = new HashMap<String, Location>();
    @XmlElementWrapper(name = "regions")
    protected final Map<String, CuboidRegion> regions              = new HashMap<String, CuboidRegion>();
    
    @XmlAttribute(name = "locationsType")
    protected LocationsType                   locationsType        = LocationsType.ABSOLUTE;
    
    @XmlAttribute(name = "maxPlayers")
    protected int                             maxPlayers           = 16;                                 // Default value of 16.
                                                                                                          
    @XmlAttribute(name = "protectedRegion")
    protected CuboidRegion                    protectedRegion;
    
    @XmlElement(name = "anchor")
    // Used only if locationsType is RELATIVE.
    protected Location                        anchor               = null;
    
    /**
     * Creates a new MapData with specified author and name.
     * 
     * @param name
     *            name of map
     * @param author
     *            author of map
     */
    public MapData(final String name, final String author) {
        this.name = name;
        this.author = author;
    }
    
    /**
     * Initialization method for {@link AdvancedArena} classes.
     */
    public void init(final int maxPlayers, final int minPlayers,
            final int countdownLength, final Location spawnLocation,
            final CuboidRegion protectedRegion) {
        this.maxPlayers = maxPlayers;
        this.options_int.put(MapData.KEY_COUNTDOWN_LENGHT, countdownLength);
        this.options_int.put(MapData.KEY_MINIMAL_PLAYERS, minPlayers);
        this.locations.put(MapData.KEY_ARENA_SPAWN, spawnLocation);
        this.protectedRegion = protectedRegion;
    }
    
    public MapData() {
        
    }
    
    public static final MapData load(final File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(MapData.class);
        Unmarshaller un = jc.createUnmarshaller();
        return (MapData) un.unmarshal(file);
    }
    
    public void save(final File file) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(MapData.class);
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(this, file);
    }
    
    public String getOption_String(final String key) {
        return this.options_string.get(key);
    }
    
    public int getOption_Integer(final String key) {
        return this.options_int.get(key);
    }
    
    public String getMinigameName() {
        return this.minigameName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public Location getLocation(final String key) {
        if (this.locationsType == LocationsType.ABSOLUTE) {
            return this.locations.get(key);
        }
        else {
            if (this.anchor != null) {
                return this.anchor.add(this.locations.get(key));
            }
            else {
                throw new InvalidMapDataException(
                        "Can't return relatiive location when anchor is null.");
            }
        }
    }
    
    public CuboidRegion getRegion(final String key) {
        if (this.locationsType == LocationsType.ABSOLUTE) {
            return this.regions.get(key);
        }
        else {
            return RegionTransformer.toAbsolute(this.regions.get(key), this.anchor);
        }
    }
    
    public Map<String, String> getOptionsString() {
        return this.options_string;
    }
    
    public Map<String, Integer> getOptionsNumber() {
        return this.options_int;
    }
    
    public Map<String, Location> getLocations() {
        return this.locations;
    }
    
    public Map<String, CuboidRegion> getRegions() {
        return this.regions;
    }
    
    public CuboidRegion getProtectedRegion() {
        return this.protectedRegion;
    }
    
    public UUID getWorld() {
        return this.protectedRegion.getWorld();
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
}
