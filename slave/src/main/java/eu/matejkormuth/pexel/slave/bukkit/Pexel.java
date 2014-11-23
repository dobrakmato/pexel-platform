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
package eu.matejkormuth.pexel.slave.bukkit;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import eu.matejkormuth.pexel.slave.boot.PexelSlaveBukkitPlugin;
import eu.matejkormuth.pexel.slave.bukkit.core.Achievements;
import eu.matejkormuth.pexel.slave.bukkit.core.Auth;
import eu.matejkormuth.pexel.slave.bukkit.core.MagicClock;
import eu.matejkormuth.pexel.slave.bukkit.core.Scheduler;
import eu.matejkormuth.pexel.slave.bukkit.util.AsyncWorker;
import eu.matejkormuth.pexel.slave.bukkit.util.PlayerFreezer;

/**
 * Class used for API calls.
 */
public final class Pexel {
    //Pexel plugin.
    private static PexelCore instance;
    //Instance of random. 
    private static Random    random = new Random();
    
    protected final static void initialize(final PexelCore plugin) {
        if (Pexel.instance == null)
            Pexel.instance = plugin;
        else
            throw new RuntimeException("Pexel object already initialized!");
    }
    
    /**
     * Returns the main plugin instance.
     * 
     * @return core
     */
    public static final PexelSlaveBukkitPlugin getCore() {
        return PexelSlaveBukkitPlugin.getInstance();
    }
    
    /**
     * Returns {@link Achievements} class.
     * 
     * @return achievements
     */
    public static final Achievements getAchievements() {
        return Pexel.instance.achievementsClient;
    }
    
    /**
     * Returns player freezer.
     * 
     * @return player freezer
     */
    public static final PlayerFreezer getPlayerFreezer() {
        return Pexel.instance.freezer;
    }
    
    /**
     * Returns instance of {@link Random}.
     * 
     * @return pexel's {@link Random}.
     */
    public final static Random getRandom() {
        return Pexel.random;
    }
    
    /**
     * Returns event processor.
     * 
     * @return {@link EventProcessor} instance.
     */
    public final static EventProcessor getEventProcessor() {
        return Pexel.instance.eventProcessor;
    }
    
    /**
     * Returns pexel's magic clock class.
     * 
     * @return {@link MagicClock} instance.
     */
    public final static MagicClock getMagicClock() {
        return Pexel.instance.magicClock;
    }
    
    /**
     * Returns pexel's async wokrer instance.
     * 
     * @return {@link AsyncWorker} instance.
     */
    public final static AsyncWorker getAsyncWorker() {
        return Pexel.instance.asyncWorker;
    }
    
    /**
     * Return's hub location.
     * 
     * @return hub lcoation
     */
    public final static Location getHubLocation() {
        return new Location(Bukkit.getWorld("world"), 9.5, 47.5, 262.5);
    }
    
    /**
     * Returns pexel's async wokrer instance.
     * 
     * @return {@link Auth} instance.
     */
    public final static Auth getAuth() {
        return Pexel.instance.auth;
    }
    
    /**
     * Returns pexel's scheduler instance.
     * 
     * @return {@link Scheduler} instance.
     */
    public final static Scheduler getScheduler() {
        return Pexel.instance.scheduler;
    }
}
