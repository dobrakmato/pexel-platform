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
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import eu.matejkormuth.pexel.commons.CuboidRegion;
import eu.matejkormuth.pexel.commons.MapData;
import eu.matejkormuth.pexel.commons.SerializableLocation;
import eu.matejkormuth.pexel.slave.bukkit.actions.CommandAction;
import eu.matejkormuth.pexel.slave.bukkit.actions.TeleportAction;
import eu.matejkormuth.pexel.slave.bukkit.areas.AreaFlag;
import eu.matejkormuth.pexel.slave.bukkit.areas.Lobby;
import eu.matejkormuth.pexel.slave.bukkit.core.Achievement;
import eu.matejkormuth.pexel.slave.bukkit.core.StorageEngine;
import eu.matejkormuth.pexel.slave.bukkit.core.TeleportGate;

/**
 * Class where is all hard coded stuff stored. This class IS NETWORK DEPENDENT.
 * 
 */
public class HardCoded {
    public static Vector antigravity = new Vector(0, 0.2F, 0);
    
    /**
     * Main method called from Plugin.onEnable()
     */
    public static final void main() {
        //Initialize color war mingiame
        //new ColorWarMinigame();
        //Initialize zabi pitkesa minigame
        //new ZabiPitkesaMinigame();
        //new TntTagMinigame();
        
        //new KingdomWarsMingame();
        
        /*
         * Pexel.getBans().addBan( new PlayerBan("nemam sa rad", new NamedBanAuthor("dobrakmato"), (Player)
         * Bukkit.getOfflinePlayer("test"), Server.THIS_SERVER));
         * 
         * Pexel.getBans().addBan( new PlayerBan(100000, "dementy dvodod", new NamedBanAuthor("dement"), (Player)
         * Bukkit.getOfflinePlayer("test2"), Server.THIS_SERVER));
         * 
         * Pexel.getBans().addBan( new PlayerBan(45000, "test", new NamedBanAuthor("dobrakmato"), (Player)
         * Bukkit.getOfflinePlayer("DeathlNom"), Server.THIS_SERVER));
         */
        
        // Test XML
        class SampleArenaMap extends MapData {
            public SampleArenaMap() {
                super("Sample map", "dobrakmato");
                this.name = "sampleMap";
                this.minigameName = "sampleMinigame";
                
                this.locations.put("loc1",
                        new SerializableLocation(Pexel.getHubLocation()));
                this.locations.put("testloc",
                        SerializableLocation.fromLocation(new Location(
                                Bukkit.getWorld("world"), 16, 32, 64)));
                
                this.options_string.put("option1", "yes");
                this.options_string.put("option2", "yes");
                this.options_string.put("option3", "no");
                
                this.options_int.put("option4", 225);
                
                this.regions.put("region_one", new CuboidRegion(new Vector(5, 10, 88),
                        new Vector(50, 50, 70), Bukkit.getWorld("world")));
                
                this.init(16, 4, 60, new Location(Bukkit.getWorld("world"), 11, 22, 33),
                        this.regions.get("region_one"));
            }
        }
        
        try {
            new SampleArenaMap().save(new File(Pexel.getCore()
                    .getDataFolder()
                    .getAbsolutePath()
                    + "/sampleMap.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        
        //Initialize main gates
        initGates();
        
        //Initialize lobbies
        initLobbies();
        
        //Initialize achievements
        initAchievements();
        
        // Gravity change
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Pexel.getCore(), new Runnable() {
            @Override
            public void run() {
                for (Entity e : Bukkit.getWorld("space").getEntities()) {
                    if (!e.isOnGround()) {
                        if (e instanceof Player) {
                            if (!((Player) e).isFlying()) {
                                e.setVelocity(e.getVelocity().add(HardCoded.antigravity));
                            }
                        }
                        e.setVelocity(e.getVelocity().add(HardCoded.antigravity));
                    }
                }
            }
        }, 0L, 1L);
    }
    
    private static void initAchievements() {
        Pexel.getAchievements().registerAchievement(
                Achievement.global("allgames", "Tester",
                        "Play all minigames on network!", 1));
    }
    
    private static void initLobbies() {
        StorageEngine.addLobby(new Lobby("hub", new CuboidRegion(
                new Vector(52, 107, 226), new Vector(-30, 1, 303),
                Bukkit.getWorld("world"))));
        
        StorageEngine.getLobby("hub").setThresholdY(10);
        
        //dobrakmato - block interactions
        StorageEngine.getLobby("hub").setPlayerFlag(AreaFlag.BLOCK_BREAK, true,
                UUID.fromString("966ad920-d45e-3fe5-8956-bf7a7a877ab4"));
        StorageEngine.getLobby("hub").setPlayerFlag(AreaFlag.BLOCK_PLACE, true,
                UUID.fromString("966ad920-d45e-3fe5-8956-bf7a7a877ab4"));
        
        StorageEngine.addLobby(new Lobby("minigamelobby", new CuboidRegion(new Vector(
                2038, 0, 2571), new Vector(1910, 255, 2437), Bukkit.getWorld("world"))));
        
        StorageEngine.getLobby("minigamelobby").setSpawn(
                new Location(Bukkit.getWorld("world"), 1972.5, 148, 2492.5));
        
        StorageEngine.getLobby("minigamelobby").setPlayerFlag(AreaFlag.BLOCK_BREAK,
                true, UUID.fromString("966ad920-d45e-3fe5-8956-bf7a7a877ab4"));
        StorageEngine.getLobby("minigamelobby").setPlayerFlag(AreaFlag.BLOCK_PLACE,
                true, UUID.fromString("966ad920-d45e-3fe5-8956-bf7a7a877ab4"));
    }
    
    private static void initGates() {
        StorageEngine.addGate("Lsurvival", new TeleportGate(new CuboidRegion(new Vector(
                -7, 50, 258), new Vector(-9, 54, 264), Bukkit.getWorld("world")),
                new TeleportAction(null, null)));
        
        StorageEngine.addGate("Lstarving", new TeleportGate(new CuboidRegion(new Vector(
                26, 50, 266), new Vector(28, 55, 260), Bukkit.getWorld("world")),
                new TeleportAction(null, null)));
        
        // StorageEngine.addGate("Lminigame", new TeleportGate(new CuboidRegion(new Vector(
        //        7, 50, 280), new Vector(13, 55, 282), Bukkit.getWorld("world")),
        ///        new TeleportAction(new Location(Bukkit.getWorld("world"), 1972.5, 147.5,
        //               2492.5), ServerInfo.localServer())));
        
        //Initialize gates
        StorageEngine.addGate("mg_colorwar",
                new TeleportGate(new CuboidRegion(new Vector(1976, 147, 2532),
                        new Vector(1972, 153, 2534), Bukkit.getWorld("world")),
                        new CommandAction("pcmd cwtest")));
        
        StorageEngine.addGate("mg_tnttag", new TeleportGate(
                new CuboidRegion(new Vector(1962, 147, 2532),
                        new Vector(1967, 153, 2534), Bukkit.getWorld("world")),
                new CommandAction("pcmd tnttest")));
    }
}
