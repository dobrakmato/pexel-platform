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

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;

public class TimeBomb {
    private final Block       signBlock;
    private final Block       tntBlock;
    private int               timeLeft = 60;
    private final BukkitTimer timer;
    
    public TimeBomb(final Block tntblock, final Block sign, final int timeLeft) {
        this.timer = new BukkitTimer(20, new Runnable() {
            @Override
            public void run() {
                TimeBomb.this.tick();
            }
        });
        this.timer.start();
        
        Validate.notNull(tntblock);
        Validate.notNull(sign);
        
        this.signBlock = sign;
        this.tntBlock = tntblock;
        this.timeLeft = timeLeft;
        
        Sign s = (Sign) this.signBlock.getState();
        s.setLine(0, "====================");
        s.setLine(2, "====================");
        s.update();
    }
    
    protected void tick() {
        this.timeLeft--;
        this.update(this.timeLeft);
    }
    
    public void update(final int timeLeft) {
        Sign s = (Sign) this.signBlock.getState();
        
        if (this.timeLeft < 10) {
            s.setLine(1, ChatColor.RED + "00:" + Formatter.formatTimeLeft(this.timeLeft)
                    + "." + ChatColor.MAGIC + "00");
        }
        else {
            s.setLine(1, "00:" + Formatter.formatTimeLeft(this.timeLeft) + "."
                    + ChatColor.MAGIC + "00");
        }
        s.update();
        
        if (this.timeLeft <= 0) {
            this.timer.stop();
            this.tntBlock.setType(Material.AIR);
            this.signBlock.setType(Material.AIR);
            this.tntBlock.getLocation().getWorld().spawnEntity(
                    this.tntBlock.getLocation(), EntityType.PRIMED_TNT);
        }
    }
}
