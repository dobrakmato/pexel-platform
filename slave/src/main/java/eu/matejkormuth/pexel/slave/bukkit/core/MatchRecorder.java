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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.text.ChatColor;
import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.arenas.AbstractArena;

/**
 * Recording device for matches.
 * 
 * @author Mato Kormuth
 * 
 */
public class MatchRecorder {
    // TODO: FIXME: XXX: Need re-writing...
    
    //Arena that this recorder record.
    private final AbstractArena      arena;
    //ID of periodic task
    private int                      taskId      = 0;
    //Interval in ticks
    private final long               interval    = 2L;
    //List of frames
    private final List<Frame>        frames      = new ArrayList<Frame>(600);
    //Mapping UUID to player name
    private final Map<UUID, String>  playernames = new HashMap<UUID, String>();
    //Mapping UUID to EntityID
    private final Map<UUID, Integer> playerids   = new HashMap<UUID, Integer>();
    
    /**
     * Initializes new instance of record for specified arena
     * 
     * @param arena
     *            arena to initialize recorder to
     */
    public MatchRecorder(final AbstractArena arena) {
        this.arena = arena;
    }
    
    /**
     * Starts periodic process of capturing frames.
     */
    public void startCapturing() {
        this.arena.chatAll(ChatColor.RED + "[Record] " + ChatColor.GOLD
                + "Warning, this match is recorded!");
        this.arena.chatAll(ChatColor.RED + "[Record] " + ChatColor.GOLD
                + "Recording started!");
        
        for (Player p : this.arena.getPlayers()) {
            this.playernames.put(p.getUniqueId(), p.getName());
            this.playerids.put(p.getUniqueId(), p.getEntityId());
        }
        
        this.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(new Runnable() {
            @Override
            public void run() {
                MatchRecorder.this.captureFrame();
            }
        }, 0L, this.interval);
    }
    
    /**
     * Makes snapshot of player healths and positions ak. records a frame.
     */
    protected void captureFrame() {
        Frame frame = new Frame();
        for (Player p : this.arena.getPlayers()) {
            frame.p_locations.put(p.getEntityId(), p.getLocation());
            frame.p_healths.put(p.getEntityId(), (p.getHealth())); //getHealth fix
        }
        this.frames.add(frame);
    }
    
    /**
     * Stops capturing process.
     */
    public void stopCapturing() {
        this.arena.chatAll(ChatColor.RED + "[Record] " + ChatColor.GOLD
                + "Recording stopped!");
        Pexel.getScheduler().cancelTask(this.taskId);
    }
    
    /**
     * Returns whether is recorder recording or not.
     * 
     * @return true if is recorder enabled
     */
    public boolean isEnabled() {
        return this.taskId != 0;
    }
    
    /**
     * Saves recorder frames to file.
     */
    public void save() {
        Log.info("Saving started!");
        //TODO: FIXME: Please, make this thing normal...
        long starttime = System.nanoTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SS");
        String name = Paths.matchRecord(sdf.format(new Date()) + "-"
                + this.arena.getName().toLowerCase());
        
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(new File(name)));
            
            writer.write("# MATCH RECORD INFO START\n");
            writer.write("version=1\n");
            writer.write("interval=" + this.interval + "\n");
            writer.write("# MATCH RECORD INFO END\n");
            
            writer.write("# MINIGAME INFO START\n");
            writer.write("minigameName=" + this.arena.getMinigame().getName() + "\n");
            writer.write("arenaName=" + this.arena.getName() + "\n");
            writer.write("date=" + System.currentTimeMillis() + "\n");
            writer.write("# MINIGAME INFO END\n");
            
            writer.write("# NAME TRANSLATE MAP START\n");
            for (Entry<UUID, String> entry : this.playernames.entrySet())
                writer.write(entry.getKey().toString() + "=" + entry.getValue() + "\n");
            writer.write("# NAME TRANSLATE MAP END\n");
            
            writer.write("# ID TRANSLATE MAP START\n");
            for (Entry<UUID, Integer> entry : this.playerids.entrySet())
                writer.write(entry.getKey().toString() + "=" + entry.getValue() + "\n");
            writer.write("# ID TRANSLATE MAP END\n");
            
            writer.write("# FRAMES SECTION START\n");
            
            List<Frame> frames2 = this.frames;
            int frameCount = frames2.size();
            for (int i = 0; i < frameCount; i++) {
                Frame f = frames2.get(i);
                writer.write("# FRAME " + i + " START\n");
                
                writer.write("# FRAME PLAYER LOCATIONS LIST START\n");
                for (Entry<Integer, Location> entry : f.p_locations.entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue().getX() + "|"
                            + entry.getValue().getY() + "|" + entry.getValue().getZ()
                            + "|" + entry.getValue().getYaw() + "|"
                            + entry.getValue().getPitch() + "\n");
                }
                writer.write("# FRAME PLAYER LOCATIONS LIST END\n");
                
                writer.write("# FRAME PLAYER HEALTH LIST START\n");
                for (Entry<Integer, Double> entry : f.p_healths.entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
                }
                writer.write("# FRAME PLAYER HEALTH LIST END\n");
                
                writer.write("# FRAME " + i + " END\n");
            }
            writer.write("# FRAMES SECTION END\n");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.info("Saving ended! (took " + (System.nanoTime() - starttime) + " ns / "
                + (System.nanoTime() - starttime) / 1000 / 1000 + "ms)");
    }
    
    /**
     * Frame.
     * 
     * @author Mato Kormuth
     * 
     */
    public class Frame {
        //Various mappings.
        Map<Integer, Location> p_locations = new HashMap<Integer, Location>();
        Map<Integer, Double>   p_healths   = new HashMap<Integer, Double>();
    }
    
    /**
     * Resets recorder.
     */
    public void reset() {
        this.frames.clear();
        this.playerids.clear();
        this.playernames.clear();
        
        Pexel.getScheduler().cancelTask(this.taskId);
    }
}
