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
package eu.matejkormuth.pexel.slave.bukkit.rollback;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

/**
 * Class that stores information about change in blocks.
 * 
 * @author Mato Kormuth
 * 
 */
public class BlockChange {
    //Values before change.
    private final Material     oldMaterial;
    private final MaterialData oldMaterialData;
    //Location.
    private final Location     blockLocation;
    
    /**
     * Creates a new block change object.
     * 
     * @param oldMaterial
     *            old material
     * @param oldMaterialData
     *            old data
     * @param blockLocation
     *            location of block.
     */
    public BlockChange(final Material oldMaterial, final MaterialData oldMaterialData,
            final Location blockLocation) {
        this.oldMaterial = oldMaterial;
        this.oldMaterialData = oldMaterialData;
        this.blockLocation = blockLocation;
    }
    
    /**
     * Creates a new block change from Block.
     * 
     * @param block
     *            the state of block before change
     */
    public BlockChange(final Block block) {
        this.oldMaterial = block.getType();
        this.oldMaterialData = block.getState().getData();
        this.blockLocation = block.getLocation();
    }
    
    /**
     * Changes block to its state before change.
     */
    public void applyRollback() {
        BlockState state = this.blockLocation.getBlock().getState();
        state.setType(this.oldMaterial);
        state.setData(this.oldMaterialData);
        state.update(true, false); //Do not apply physics on rollbacks.
    }
    
    /**
     * Returns the location of block.
     * 
     * @return the location
     */
    public Location getLocation() {
        return this.blockLocation;
    }
}
