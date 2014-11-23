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
package eu.matejkormuth.pexel.slave.bukkit.cinematics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Reprezentuje zakladny nahravac
 * 
 * @author Mato Kormuth
 * 
 */
public class BasicRecorder {
    private Player  player;
    private boolean recording;
    private long    frames = 0;
    private int     ID     = 0;
    
    /**
     * Pocet FPS v tomto nahravaci.
     */
    public int      FPS    = 20;
    
    /**
     * Vytvori instanciu nahravaca.
     * 
     * @param p
     *            hrac, podla ktoreho nahrava
     * @param fps
     *            pocet obrazkov za sekundu
     */
    public BasicRecorder(final Player p, final int fps) {
        this.player = p;
        this.FPS = fps;
    }
    
    /**
     * Nastavi ID nahravaca.
     * 
     * @param id
     *            id
     */
    public void setID(final int id) {
        this.ID = id;
    }
    
    /**
     * Zacne nahravat.
     */
    public void record() {
        this.player.sendMessage("Recording " + ChatColor.RED + "[ID " + this.ID + "] "
                + ChatColor.YELLOW + " (" + this.FPS + "fps) " + ChatColor.GREEN
                + " has started...");
        
        if (this.FPS > 20)
            this.player.sendMessage(ChatColor.RED
                    + "Nahravanie na VIAC AKO 20 FPS nema vyznam! Vysledny zaznam bude rovnaky pri pouziti 20 aj 60 fps.");
        
        this.recording = true;
        
        //Start recording thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                BasicRecorder.this._record();
            }
        }).start();
    }
    
    /**
     * Zastavi nahravanie.
     */
    public void stop() {
        this.player.sendMessage("Stopped! Recorded " + this.frames + " frames ("
                + (this.frames / this.FPS) + " seconds)...");
        this.recording = false;
        this.player.sendMessage("Saved as: recording" + System.currentTimeMillis()
                + ".dat");
    }
    
    /**
     * Interna metoda urcena na nahravanie.
     */
    private void _record() {
        while (this.recording) {
            if ((this.frames % 100) == 0) {
                if (this.FPS > 20)
                    this.player.sendMessage(ChatColor.RED
                            + "Nahravanie na VIAC AKO 20 FPS nema vyznam! Vysledny zaznam bude rovnaky pri pouziti 20 aj 60 fps.");
                this.player.sendMessage("Recorded " + this.frames + " frames ("
                        + (this.frames / this.FPS) + " seconds)");
            }
            this.frames++;
            
            if (this.frames > 30000) {
                //prekrocene maximum
                this.player.sendMessage("Zastavujem nahravanie. Prekrocene maximum frameov.");
                this.stop();
            }
            
            try {
                Thread.sleep(1000 / this.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Vrati player.
     * 
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * Nastavi hraca.
     * 
     * @param player
     *            hrac
     */
    public void setPlayer(final Player player) {
        this.player = player;
    }
}
