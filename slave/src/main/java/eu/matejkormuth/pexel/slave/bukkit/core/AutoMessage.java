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
package eu.matejkormuth.pexel.slave.bukkit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * AutoMessage.
 * 
 */
public class AutoMessage implements Updatable {
    private static final List<String> strings  = new ArrayList<String>();
    private static final String       prefix   = "";
    private static final long         interval = 20000 / 30;             //each 60 seconds.
                                                                          
    private static final Random       random   = new Random();
    private static int                taskId   = 0;
    private static boolean            enabled  = false;
    
    static {
        AutoMessage.strings.add("Hras " + ChatColor.BLUE + "BETA" + ChatColor.RESET
                + " verziu pexel-u. Dakujeme!");
        AutoMessage.strings.add("Navstivte aj nasu web stranku www.pexel.eu (v priprave)!");
        AutoMessage.strings.add("Hras " + ChatColor.BLUE + "BETA" + ChatColor.RESET
                + " verziu pexel-u. Dakujeme!");
        AutoMessage.strings.add("Ak mas nejake pripomienky, povedz nam na "
                + ChatColor.GREEN + "ts.mertex.eu!");
    }
    
    /**
     * Boradcast random message to chat.
     */
    public static void pushMessage() {
        Bukkit.broadcastMessage(AutoMessage.prefix
                + AutoMessage.strings.get(AutoMessage.random.nextInt(AutoMessage.strings.size())));
    }
    
    /**
     * Returns if is AutoMessage enabled.
     * 
     * @return true or false
     */
    public static boolean isEnabled() {
        return AutoMessage.enabled;
    }
    
    @Override
    public void updateStart() {
        Log.partEnable("Automessage");
        UpdatedParts.registerPart(this);
        AutoMessage.enabled = true;
        AutoMessage.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(
                new Runnable() {
                    @Override
                    public void run() {
                        AutoMessage.pushMessage();
                    }
                }, 0, AutoMessage.interval);
    }
    
    @Override
    public void updateStop() {
        Log.partDisable("Automessage");
        AutoMessage.enabled = false;
        Pexel.getScheduler().cancelTask(AutoMessage.taskId);
    }
}
