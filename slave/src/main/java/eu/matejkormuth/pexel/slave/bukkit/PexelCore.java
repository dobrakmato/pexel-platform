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

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.plugin.java.JavaPlugin;

import eu.matejkormuth.pexel.slave.bukkit.commands.AlternativeCommands;
import eu.matejkormuth.pexel.slave.bukkit.commands.BukkitCommandManager;
import eu.matejkormuth.pexel.slave.bukkit.commands.ChannelCommand;
import eu.matejkormuth.pexel.slave.bukkit.commands.CommandManager;
import eu.matejkormuth.pexel.slave.bukkit.commands.FriendCommand;
import eu.matejkormuth.pexel.slave.bukkit.commands.PCMDCommand;
import eu.matejkormuth.pexel.slave.bukkit.commands.SettingsCommand;
import eu.matejkormuth.pexel.slave.bukkit.commands.SpawnCommand;
import eu.matejkormuth.pexel.slave.bukkit.commands.UnfriendCommand;
import eu.matejkormuth.pexel.slave.bukkit.core.Achievements;
import eu.matejkormuth.pexel.slave.bukkit.core.Auth;
import eu.matejkormuth.pexel.slave.bukkit.core.AutoMessage;
import eu.matejkormuth.pexel.slave.bukkit.core.License;
import eu.matejkormuth.pexel.slave.bukkit.core.Log;
import eu.matejkormuth.pexel.slave.bukkit.core.MagicClock;
import eu.matejkormuth.pexel.slave.bukkit.core.Scheduler;
import eu.matejkormuth.pexel.slave.bukkit.core.StorageEngine;
import eu.matejkormuth.pexel.slave.bukkit.core.UpdatedParts;
import eu.matejkormuth.pexel.slave.bukkit.util.AsyncWorker;
import eu.matejkormuth.pexel.slave.bukkit.util.PlayerFreezer;

/**
 * Bukkit plugin class.
 */
public class PexelCore {
    protected JavaPlugin plugin;
    
    public PexelCore(final File datafodler, final JavaPlugin plugin) {
        this.datafolder = datafodler;
        this.plugin = plugin;
    }
    
    /**
     * Pexel matchmaking.
     */
    //public Matchmaking    matchmaking;
    /**
     * Player freezer.
     */
    public PlayerFreezer  freezer;
    /**
     * Eent processor.
     */
    public EventProcessor eventProcessor;
    /**
     * Magic clock instance.
     */
    public MagicClock     magicClock;
    /**
     * AutoMessage instance.
     */
    public AutoMessage    message;
    /**
     * AsyncWorker object.
     */
    public AsyncWorker    asyncWorker;
    /**
     * Pexel auth object.
     */
    public Auth           auth;
    /**
     * Pexel scheduler object.
     */
    public Scheduler      scheduler;
    /**
     * Pexel command manager.
     */
    public CommandManager commandManager;
    /**
     * Pexel Ban storage.
     */
    //public BanStorage     banStorage;
    public Achievements   achievementsClient;
    private final File    datafolder;
    
    @SuppressWarnings("deprecation")
    public void onDisable() {
        Log.partDisable("Core");
        //Shutdown all updated parts.
        UpdatedParts.shutdown();
        
        //this.banStorage.save();
        
        //Save important data.
        StorageEngine.saveData(); //oldway
        
        StorageEngine.saveArenas();
        StorageEngine.saveProfiles();
        
        this.asyncWorker.shutdown();
        
        Log.partDisable("Core");
    }
    
    public void onEnable() {
        Log.partEnable("Core");
        
        try {
            this.loadLibs();
        } catch (MalformedURLException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        
        // Print license to console.
        License.print();
        
        Pexel.initialize(this);
        this.createDirectoryStructure();
        
        this.freezer = new PlayerFreezer();
        
        this.scheduler = new Scheduler();
        
        this.message = new AutoMessage();
        this.message.updateStart();
        
        //this.banStorage = new BanStorage();
        //this.banStorage.load();
        
        this.auth = new Auth();
        
        //this.matchmaking = new Matchmaking();
        //this.matchmaking.updateStart();
        
        this.achievementsClient = new Achievements();
        
        try {
            //this.matchmakingSignUpdater = new MatchmakingSignUpdater();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
        this.magicClock = new MagicClock();
        
        this.asyncWorker = new AsyncWorker(3);
        this.asyncWorker.start();
        
        this.eventProcessor = new EventProcessor();
        
        // Bukkit way
        // DEBUKKITIZE commands
        //this.plugin.getCommand("arena").setExecutor(new ArenaCommand());
        this.plugin.getCommand("friend").setExecutor(new FriendCommand());
        this.plugin.getCommand("unfriend").setExecutor(new UnfriendCommand());
        this.plugin.getCommand("settings").setExecutor(new SettingsCommand());
        //this.plugin.getCommand("party").setExecutor(new PartyCommand());
        //this.plugin.getCommand("lobbyarena").setExecutor(new LobbyCommand());
        //this.plugin.getCommand("qj").setExecutor(new QJCommand());
        this.plugin.getCommand("spawn").setExecutor(new SpawnCommand());
        //this.plugin.getCommand("gate").setExecutor(new GateCommand());
        this.plugin.getCommand("pcmd").setExecutor(new PCMDCommand());
        
        // Pexel way
        this.commandManager = new BukkitCommandManager();
        //this.commandManager.registerCommands(new PartyCommand());
        this.commandManager.registerCommands(new ChannelCommand());
        //this.commandManager.registerCommands(new MatchmakingCommand());
        
        StorageEngine.initialize(this);
        StorageEngine.loadData();
        
        new AlternativeCommands();
        
        HardCoded.main();
    }
    
    private void loadLibs() throws MalformedURLException, ClassNotFoundException {
        Log.info("Loading external libraries...");
        
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<?>[] parameters = new Class[] { URL.class };
        Class<?> sysclass = URLClassLoader.class;
        
        for (File f : new File(this.getDataFolder().getAbsolutePath() + "/libs").listFiles()) {
            try {
                Log.info("[P-LIBLOAD] Loading " + f.getAbsolutePath());
                Method method = sysclass.getDeclaredMethod("addURL", parameters);
                method.setAccessible(true);
                method.invoke(sysloader, new Object[] { f.toURI().toURL() });
            } catch (Throwable t) {
                t.printStackTrace();
                Log.severe("Error, could not add URL (" + f.getAbsolutePath()
                        + ") to system classloader");
            }
        }
    }
    
    private File getDataFolder() {
        return this.datafolder;
    }
    
    private void createDirectoryStructure() {
        boolean created = false;
        String path = this.getDataFolder().getAbsolutePath();
        created |= new File(path + "/arenas").mkdirs();
        created |= new File(path + "/cache").mkdirs();
        created |= new File(path + "/records").mkdirs();
        created |= new File(path + "/profiles").mkdirs();
        created |= new File(path + "/clips").mkdirs();
        if (created)
            Log.info("Directory structure expanded!");
    }
}
