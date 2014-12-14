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
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import eu.matejkormuth.pexel.commons.math.Vector3d;

/**
 * Class that represents cuboid region.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CuboidRegion implements Region {
    // Random for getRandomLocation calls.
    private static Random    random = new Random();
    
    /**
     * First vector.
     */
    @XmlElement(name = "vector1")
    protected final Vector3d v1;
    /**
     * Second vector.
     */
    @XmlElement(name = "vector2")
    protected final Vector3d v2;
    
    @XmlElement(name = "world")
    protected final UUID     world;
    
    /**
     * Creates a new region from two vectors and one world.
     * 
     * @param v1
     *            minimum point
     * @param v2
     *            maximum point
     * @param world
     *            world
     */
    public CuboidRegion(final Vector3d v1, final Vector3d v2, final UUID world) {
        this.v1 = v1;
        this.v2 = v2;
        this.world = world;
    }
    
    /**
     * Creates a new region with center and size.
     * 
     * @param center
     *            center
     * @param w
     *            world
     * @param size
     *            size
     */
    public CuboidRegion(final Vector3d center, final int size, final UUID world) {
        this.v1 = center.add(new Vector3d(size, size, size));
        this.v2 = center.add(new Vector3d(-size, -size, -size));
        this.world = world;
    }
    
    /**
     * Returns whether the location intersect the region.
     * 
     * @param loc
     *            location to check.
     * @return
     */
    public boolean intersects(final Location loc) {
        if (this.world.equals(loc.getWorld()))
            return this.intersects(loc.toVector());
        else
            return false;
    }
    
    /**
     * Returns whether the location intersects X and Z coordinate of this region.
     * 
     * @param loc
     *            location to check
     * @return
     */
    public boolean intersectsXZ(final Location loc) {
        if (this.world.equals(loc.getWorld()))
            return this.intersectsXZ(loc.toVector());
        else
            return false;
    }
    
    /**
     * Returns whatever vector intersects the region.
     * 
     * @param v
     *            vector to check
     * @return
     */
    public boolean intersects(final Vector3d v) {
        return CuboidRegion.range(this.v1.getX(), this.v2.getX(), v.getX())
                && CuboidRegion.range(this.v1.getY(), this.v2.getY(), v.getY())
                && CuboidRegion.range(this.v1.getZ(), this.v2.getZ(), v.getZ());
    }
    
    public boolean intersectsXZ(final Vector3d v) {
        return CuboidRegion.range(this.v1.getX(), this.v2.getX(), v.getX())
                && CuboidRegion.range(this.v1.getZ(), this.v2.getZ(), v.getZ());
    }
    
    /**
     * Returns players in region.
     * 
     * @param players
     *            list of players to search for
     * @return list of players
     */
    public List<Player> getPlayersXYZ(final List<Player> players) {
        List<Player> vplayers = new ArrayList<Player>();
        for (Player player : players) {
            if (this.intersects(player.getLocation())) {
                vplayers.add(player);
            }
        }
        return vplayers;
    }
    
    private final static boolean range(final double min, final double max,
            final double value) {
        if (max > min)
            return (value <= max ? (value >= min ? true : false) : false);
        else
            return (value <= min ? (value >= max ? true : false) : false);
    }
    
    /**
     * Retruns first vector.
     * 
     * @return vector
     */
    public Location getV1Loaction() {
        return new Location(this.v1.getX(), this.v1.getY(), this.v1.getZ(), this.world);
    }
    
    /**
     * Returns second vector.
     * 
     * @return vector
     */
    public Location getV2Location() {
        return new Location(this.v2.getX(), this.v2.getY(), this.v2.getZ(), this.world);
    }
    
    @Override
    public String toString() {
        return "CuboidRegion [v1=" + this.v1 + ", v2=" + this.v2 + ", world="
                + this.world + "]";
    }
    
    /**
     * Returns players in XZ region.
     * 
     * @param players
     *            list of players to search for
     * @return list of players.
     */
    public List<Player> getPlayersXZ(final List<Player> players) {
        List<Player> vplayers = new ArrayList<Player>();
        for (Player player : players) {
            if (this.intersectsXZ(player.getLocation())) {
                vplayers.add(player);
            }
        }
        return vplayers;
    }
    
    /**
     * Returns maximum X co-ordinate value of this region.
     * 
     * @return max x value
     */
    public double getMaxX() {
        if (this.v1.getX() > this.v2.getX())
            return this.v1.getX();
        else
            return this.v2.getX();
    }
    
    /**
     * Returns maximum Y co-ordinate value of this region.
     * 
     * @return max y value
     */
    public double getMaxY() {
        if (this.v1.getY() > this.v2.getY())
            return this.v1.getY();
        else
            return this.v2.getY();
    }
    
    /**
     * Returns maximum Z co-ordinate value of this region.
     * 
     * @return max z value
     */
    public double getMaxZ() {
        if (this.v1.getZ() > this.v2.getZ())
            return this.v1.getZ();
        else
            return this.v2.getZ();
    }
    
    /**
     * Returns minumum X co-ordinate value of this region.
     * 
     * @return min x value
     */
    public double getMinX() {
        if (this.v1.getX() > this.v2.getX())
            return this.v2.getX();
        else
            return this.v1.getX();
    }
    
    /**
     * Returns minumum Y co-ordinate value of this region.
     * 
     * @return min y value
     */
    public double getMinY() {
        if (this.v1.getY() > this.v2.getY())
            return this.v2.getY();
        else
            return this.v1.getY();
    }
    
    /**
     * Returns minumum Z co-ordinate value of this region.
     * 
     * @return min z value
     */
    public double getMinZ() {
        if (this.v1.getZ() > this.v2.getZ())
            return this.v2.getZ();
        else
            return this.v1.getZ();
    }
    
    public UUID getWorld() {
        return this.world;
    }
    
    /**
     * Returns random location from this region.
     * 
     * @return random location in bounds of this region
     */
    public Location getRandomLocation() {
        int a = this.rnd((int) (this.getMaxX() - this.getMinX()));
        int b = this.rnd((int) (this.getMaxY() - this.getMinY()));
        int c = this.rnd((int) (this.getMaxZ() - this.getMinZ()));
        return new Location(this.getMinX() + a, this.getMinY() + b, this.getMinZ() + c,
                this.world);
    }
    
    private int rnd(final int max) {
        if (max == 0) {
            return 0;
        }
        else {
            return CuboidRegion.random.nextInt(max);
        }
    }
    
    /**
     * Creates region around specified location with specified size.
     * 
     * @param center
     *            center
     * @param size
     *            size
     * @return region
     */
    public static CuboidRegion createAround(final Location center, final int size) {
        return new CuboidRegion(center.toVector().add(new Vector3d(size, size, size)),
                center.toVector().subtract(new Vector3d(size, size, size)),
                center.getWorld());
    }
    
    /**
     * Returns width of this region.
     * 
     * @return width
     */
    public double getWidth() {
        return this.getMaxX() - this.getMinX();
    }
    
    /**
     * Returns height of this region.
     * 
     * @return height
     */
    public double getHeight() {
        return this.getMaxY() - this.getMinY();
    }
    
    /**
     * Returns length of this region.
     * 
     * @return length
     */
    public double getLength() {
        return this.getMaxZ() - this.getMinZ();
    }
}
