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

import java.io.File;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.event.Subscribe;

import eu.matejkormuth.pexel.commons.SlaveMinecraftServerType;
import eu.matejkormuth.pexel.slave.PexelSlave;

/**
 * Sponge bootloader.
 */
@Plugin(id = "pexelslave", name = "Pexel Slave Agent", version = "1.0")
public class PexelSlaveSpongePlugin {
    public static Game game;
    
    @Subscribe
    public void onEnabled(final ServerStartedEvent event) {
        System.out.println("[BOOT] Bootstrapping PexelSlave throught PexelSlaveSpongePlugin...");
        PexelSlave.init(new File("."), SlaveMinecraftServerType.CRAFTBUKKIT);
        PexelSlaveSpongePlugin.game = event.getGame();
        // Start sync.
        //Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
        //        PexelSlave.getInstance().getSync().getOnTick(), 0L, 1L);
        //PexelSlaveBukkitPlugin.instance = this;
    }
    
    @Subscribe
    public void onDisabled(final ServerStoppedEvent event) {
        System.out.println("[BOOT] Disabling PexelSlave throught PexelSlaveBukkitPlugin...");
        // TODO: Maybe implement some safe-shutdown, so tasks in Sync wont be lost.
        PexelSlave.getInstance().shutdown();
    }
}
