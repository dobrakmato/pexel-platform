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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Reprezentuje ram/frame v klipe tretej verzie.
 * 
 * @author Mato Kormuth
 * 
 */
public class V3CameraFrame {
    /**
     * Pozicia hraca.
     */
    private Location     cameraLocation;
    /**
     * Specifikuje, ci frame obsahuje iba meta.
     */
    private boolean      isMetaOnly = false;
    /**
     * Zoznam dalsich extra dat v ramci.
     */
    private List<V3Meta> metas      = new ArrayList<V3Meta>();
    /**
     * Zoom.
     */
    private float        zoom;
    /**
     * Odkaz na klip, ktoremu ram parti.
     */
    public V3CameraClip  clip;
    
    /**
     * Vytvori novy frame.
     * 
     * @param cameraLocation
     * @param isMetaOnly
     */
    public V3CameraFrame(final Location cameraLocation, final boolean isMetaOnly) {
        this.cameraLocation = cameraLocation;
        this.camX = cameraLocation.getX();
        this.camY = cameraLocation.getY();
        this.camZ = cameraLocation.getZ();
        this.yaw = cameraLocation.getYaw();
        this.pitch = cameraLocation.getPitch();
        this.isMetaOnly = isMetaOnly;
    }
    
    public V3CameraFrame(final double x, final double y, final double z,
            final float yaw, final float pitch, final boolean isMetaOnly) {
        this.camX = x;
        this.camY = y;
        this.camZ = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.isMetaOnly = isMetaOnly;
    }
    
    public V3CameraFrame(final String line) {
        
    }
    
    public int    verzia = 3;
    public double camX;
    public double camY;
    public double camZ;
    public float  yaw;
    public float  pitch;
    
    public String serialize() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Prida zaznam o pozicii entity.
     * 
     * @param e
     */
    public void addEntityLocation(final Entity e) {
        if (this.clip.entites.contains(e)) {
            // this.clip.entites.
        }
    }
    
    /**
     * @return the playerLocation
     */
    public Location getCameraLocation() {
        return this.cameraLocation;
    }
    
    /**
     * @param playerLocation
     *            the playerLocation to set
     */
    public void setCameraLocation(final Location playerLocation) {
        this.cameraLocation = playerLocation;
    }
    
    /**
     * @return the isMeta
     */
    public boolean isMetaOnly() {
        return this.isMetaOnly;
    }
    
    /**
     * @param isMetaOnly
     *            the isMeta to set
     */
    public void setMetaOnly(final boolean isMetaOnly) {
        this.isMetaOnly = isMetaOnly;
    }
    
    /**
     * @return the extraData
     */
    public List<V3Meta> getMetas() {
        return this.metas;
    }
    
    /**
     * Prida extra data.
     * 
     * @param data
     */
    public void addMeta(final V3Meta data) {
        this.metas.add(data);
    }
    
    /**
     * @param metas
     *            the extraData to set
     */
    public void setMeta(final List<V3Meta> metas) {
        this.metas = metas;
    }
    
    /**
     * @return the zoom
     */
    public float getZoom() {
        return this.zoom;
    }
    
    /**
     * @param zoom
     *            the zoom to set
     */
    public V3CameraFrame setZoom(final float zoom) {
        this.zoom = zoom;
        return this; // For method chaining.
    }
    
    /**
     * Vrati ci tento frame ma nejake meta.
     * 
     * @return
     */
    public boolean hasMeta() {
        return (this.metas.size() != 0);
    }
    
    public int getMetaCount() {
        return this.metas.size();
    }
}
