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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;

import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntityDamage;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntityInventory;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntityRemove;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntitySpawn;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntityTeleport;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaEntityVelocity;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaFallingSand;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaParticleEffect;
import eu.matejkormuth.pexel.slave.bukkit.cinematics.v3meta.V3MetaSoundEffect;

/**
 * Nacitava skompilovane V3 klipy.
 * 
 * @author Mato Kormuth
 * 
 */
public class V3CompiledReader {
    /**
     * Stream pouzivany na nacitanie suboru.
     */
    private final DataInputStream input;
    /**
     * Kuzelne cislo 1.
     */
    public final static int       MAGIC_1 = 86;
    /**
     * Kuzelne cislo 2.
     */
    public final static int       MAGIC_2 = 114;
    /**
     * Verzia suboru.
     */
    public final static int       VERSION = 1;
    /**
     * Klip.
     */
    private V3CameraClip          clip;
    /**
     * Svet.
     */
    private World                 w;
    
    public static V3CameraClip loadFile(final String path) throws Exception {
        return new V3CompiledReader(path).clip;
    }
    
    private V3CompiledReader(final String path) throws Exception {
        this.input = new DataInputStream(new FileInputStream(path));
        this.readFile();
    }
    
    /**
     * Nacita data zo suboru do pamate.
     * 
     * @throws Exception
     */
    private void readFile() throws Exception {
        this.clip = new V3CameraClip();
        
        this.readClip();
    }
    
    /**
     * 
     * @param clip
     * @throws Exception
     */
    private void readClip() throws Exception {
        // read FileHeader.
        this.readFileHeader();
        // read Frames.
        while (true) {
            try {
                V3CameraFrame frame = this.readFrameHeader();
                this.clip.addFrame(frame);
            } catch (EOFException exc) {
                break;
            }
        }
        this.close();
    }
    
    private void readFileHeader() throws Exception {
        // Check for magic.
        byte magic1 = this.input.readByte();
        byte magic2 = this.input.readByte();
        
        if (magic1 != V3CompiledReader.MAGIC_1 || magic2 != V3CompiledReader.MAGIC_2)
            throw new Exception("This is not a valid V3C file!");
        
        this.input.readByte(); // Version byte
        this.clip.FPS = this.input.readByte();
    }
    
    private V3CameraFrame readFrameHeader() throws IOException {
        // read FrameHeader.
        double x = this.input.readDouble();
        double y = this.input.readDouble();
        double z = this.input.readDouble();
        float yaw = this.input.readFloat();
        float pitch = this.input.readFloat();
        float zoom = this.input.readFloat();
        boolean isMetaOnly = this.input.readBoolean();
        
        V3CameraFrame frame = new V3CameraFrame(
                new Location(this.w, x, y, z, yaw, pitch), isMetaOnly).setZoom(zoom);
        
        // Process meta.
        short metaCount = this.input.readShort(); // MetaCount
        
        for (short i = 0; i < metaCount; i++) {
            // Meta type
            byte metaType = this.input.readByte();
            
            switch (metaType) {
                case 0:
                    frame.addMeta(V3MetaSoundEffect.readMeta(this.input));
                    break;
                case 1:
                    frame.addMeta(V3MetaEntitySpawn.readMeta(this.input));
                    break;
                case 2:
                    frame.addMeta(V3MetaEntityDamage.readMeta(this.input));
                    break;
                case 3:
                    frame.addMeta(V3MetaEntityTeleport.readMeta(this.input));
                    break;
                case 4:
                    frame.addMeta(V3MetaEntityInventory.readMeta(this.input));
                    break;
                case 5:
                    frame.addMeta(V3MetaEntityRemove.readMeta(this.input));
                    break;
                case 6:
                    frame.addMeta(V3MetaEntityVelocity.readMeta(this.input));
                    break;
                case 7:
                    frame.addMeta(V3MetaParticleEffect.readMeta(this.input));
                    break;
                case 8:
                    frame.addMeta(V3MetaFallingSand.readMeta(this.input));
                    break;
            }
        }
        
        return frame;
    }
    
    /**
     * Zatvori subor.
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        this.input.close();
    }
}
