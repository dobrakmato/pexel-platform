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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Entity;

import eu.matejkormuth.pexel.slave.bukkit.core.Paths;

/**
 * Zlozity klip 3 urovne.
 * 
 * @author Mato Kormuth
 * 
 */
public class V3CameraClip {
    List<V3CameraFrame> frames    = Collections.synchronizedList(new ArrayList<V3CameraFrame>());
    List<Entity>        entites   = Collections.synchronizedList(new ArrayList<Entity>());
    
    /**
     * Pocet ramov za sekundu v tomto klipe.
     */
    public int          FPS       = 20;
    public int          verzia    = 3;
    public int          metaCount = 0;
    
    /**
     * Prida jeden frame do zoznamu frameov.
     * 
     * @param frame
     */
    public void addFrame(final V3CameraFrame frame) {
        frame.clip = this;
        this.frames.add(frame);
        this.metaCount += frame.getMetaCount();
    }
    
    /**
     * Prida kolekciu frameov do zoznamu frameov.
     * 
     * @param frames
     */
    public void addFrames(final List<V3CameraFrame> frames) {
        this.frames.addAll(frames);
    }
    
    /**
     * Vrati vsetky framy v klipe.
     * 
     * @return
     */
    public List<V3CameraFrame> getFrames() {
        return this.frames;
    }
    
    /**
     * Vrati frame specifikovany indexom.
     * 
     * @param index
     * @return
     */
    public V3CameraFrame getFrame(final int index) {
        return this.frames.get(index);
    }
    
    /**
     * Ulozi klip do suboru.
     * 
     * @param meno
     *            suboru.
     */
    public void save(final String name) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File(Paths.clips() + name + ".dat"));
            writer.println("#mertex-fun | CameraClip | v 1.2.1");
            writer.println("#{fsavetime=" + System.currentTimeMillis() + ",fcount="
                    + this.frames.size() + ",ver=1}");
            writer.println("#{FPS=" + this.FPS + "}");
            writer.println("#{VERSION3}");
            
            for (V3CameraFrame cframe : this.frames) {
                writer.println(cframe.serialize());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Nacita klip zo suboru.
     * 
     * @param meno
     *            suboru
     * @return klip.
     */
    public static V3CameraClip load(final String name) {
        System.out.println("Nacitavam subor " + name);
        V3CameraClip clip = new V3CameraClip();
        clip.verzia = 3;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Paths.clips() + name + ".dat"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Poznamky sa nesnaz nacitat ako bloky.
                if (!line.startsWith("#")) {
                    // Pridaj blok.
                    try {
                        clip.addFrame(new V3CameraFrame(line));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // Poznamka alebo specialne metadata?
                    if (line.startsWith("#{")) {
                        if (line.startsWith("#{FPS=")) {
                            clip.FPS = Integer.parseInt(line.substring(6,
                                    line.indexOf("}")));
                        }
                        
                        if (line.startsWith("#{VERSION3")) {
                            clip.verzia = 3;
                        }
                        // Metadata!
                        // Fajn, aj tak ich zatial nevyuzivame...
                    }
                }
                
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Nacitanych " + clip.getFrames().size() + " frameov.");
        return clip;
    }
    
    /**
     * Vrati pocet ramov v klipe.
     * 
     * @return
     */
    public int getNumOfFrames() {
        return this.frames.size();
    }
}
