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
package eu.matejkormuth.pexel.slave.bukkit.util;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Block iterator.
 */
public class BlockIterator implements Iterator<Block> {
    private final Location location;
    private int            directionX;
    private int            directionY;
    private int            directionZ;
    
    /**
     * Creates new instance of <b>BlockIterator</b>. This class should be used for iterating over blocks in specified
     * direction and should not be used to detect block patterns.
     * 
     * @param startBlock
     * @param direction
     */
    public BlockIterator(final Block startBlock, final BlockFace direction) {
        this.location = startBlock.getLocation();
        this.directionX = direction.getModX();
        this.directionY = direction.getModY();
        this.directionZ = direction.getModZ();
    }
    
    public BlockIterator(final Block startBlock, final int modX, final int modY,
            final int modZ) {
        this.location = startBlock.getLocation();
        this.directionX = modX;
        this.directionY = modY;
        this.directionZ = modZ;
    }
    
    @Override
    public boolean hasNext() {
        return true;
    }
    
    @Override
    public Block next() {
        return this.location.add(this.directionX, this.directionY, this.directionZ).getBlock();
    }
    
    /**
     * Iterates by specified direction until specified material is detected. Then it returns first block, that is from
     * specififed material.
     * 
     * @param specified
     *            specified material
     * @return first block in specified direction of specified type
     */
    public Block until(final Material specified) {
        do {
            return this.location.getBlock();
        } while (this.next().getType() == specified);
    }
    
    @Override
    public void remove() {
        this.location.getBlock().setType(Material.AIR);
    }
    
    /**
     * @param direction
     *            the direction to set
     */
    public void setDirection(final BlockFace direction) {
        this.directionX = direction.getModX();
        this.directionY = direction.getModY();
        this.directionZ = direction.getModZ();
    }
    
    public void setDirection(final int modX, final int modY, final int modZ) {
        this.directionX = modX;
        this.directionY = modY;
        this.directionZ = modZ;
    }
}
