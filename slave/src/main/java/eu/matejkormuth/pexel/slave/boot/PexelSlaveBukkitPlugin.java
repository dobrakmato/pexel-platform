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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Preconditions;

import eu.matejkormuth.pexel.commons.SlaveMinecraftServerType;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.components.managers.CommandManager;

/**
 * Bukkit (spigot compactibile) bootloader.
 */
public class PexelSlaveBukkitPlugin extends JavaPlugin implements Listener {
    private static PexelSlaveBukkitPlugin instance;
    private CommandManager                commandManager;
    
    public static PexelSlaveBukkitPlugin getInstance() {
        return PexelSlaveBukkitPlugin.instance;
    }
    
    public static void setInstance(final PexelSlaveBukkitPlugin plugin) {
        Preconditions.checkArgument(PexelSlaveBukkitPlugin.instance == null);
        PexelSlaveBukkitPlugin.instance = plugin;
    }
    
    public PexelSlaveBukkitPlugin() {
    }
    
    @Override
    public void onEnable() {
        this.getLogger().info(
                "[BOOT] Bootstrapping PexelSlave throught PexelSlaveBukkitPlugin...");
        PexelSlave.init(this.getDataFolder(), SlaveMinecraftServerType.CRAFTBUKKIT);
        // Start sync.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                PexelSlave.getInstance().getSync().getOnTick(), 0L, 1L);
        setInstance(this);
        this.commandManager = PexelSlave.getInstance()
                .getComponent(CommandManager.class);
    }
    
    @Override
    public void onDisable() {
        this.getLogger().info(
                "[BOOT] Disabling PexelSlave throught PexelSlaveBukkitPlugin...");
        // TODO: Maybe implement some safe-shutdown, so tasks in Sync wont be lost.
        PexelSlave.getInstance().shutdown();
    }
    
    @EventHandler
    public void onCommnadPreprocess(final PlayerCommandPreprocessEvent event) {
        eu.matejkormuth.pexel.commons.Player p = PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(event.getPlayer());
        if (event.getMessage().startsWith("/")) {
            boolean success = this.commandManager.parseCommand(p, event.getMessage()
                    .substring(1));
            if (success)
                event.setCancelled(true);
        }
        else {
            boolean success = this.commandManager.parseCommand(p, event.getMessage());
            if (success)
                event.setCancelled(true);
        }
    }
}
