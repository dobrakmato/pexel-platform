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
package eu.matejkormuth.pexel.slave.boot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.pexel.slave.PexelSlave;

/**
 * Bukkit (spigot compactibile) bootloader.
 */
public class PexelSlaveBukkitPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getLogger().info(
                "[BOOT] Bootstrapping PexelSlave throught PexelSlaveBukkitPlugin...");
        PexelSlave.init(this.getDataFolder());
        // Start sync.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                PexelSlave.getInstance().getSync().getOnTick(), 0L, 1L);
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info(
                "[BOOT] Disabling PexelSlave throught PexelSlaveBukkitPlugin...");
        // TODO: Maybe implement some safe-shutdown, so tasks in Sync wont be lost.
    }
}
