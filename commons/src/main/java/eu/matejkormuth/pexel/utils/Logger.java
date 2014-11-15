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
package eu.matejkormuth.pexel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that represents logger.
 */
public class Logger {
    private SimpleDateFormat format;
    private final Logger     parent;
    private final String     name;
    public boolean           timestamp;
    
    public Logger(final String name) {
        this.name = name;
        this.parent = null;
    }
    
    private Logger(final Logger parent, final String name) {
        this.parent = parent;
        this.name = name;
    }
    
    public void info(final String msg) {
        if (this.parent == null) {
            this.log("[INFO] " + msg);
        }
        else {
            this.parent.info("[" + this.name + "] " + msg);
        }
    }
    
    public void error(final String msg) {
        if (this.parent == null) {
            this.log("[ERROR] " + msg);
        }
        else {
            this.parent.error("[" + this.name + "] " + msg);
        }
    }
    
    public void warn(final String msg) {
        if (this.parent == null) {
            this.log("[WARN] " + msg);
        }
        else {
            this.parent.warn("[" + this.name + "] " + msg);
        }
    }
    
    public void debug(final String msg) {
        if (this.parent == null) {
            this.log("[DEBUG] " + msg);
        }
        else {
            this.parent.debug("[" + this.name + "] " + msg);
        }
    }
    
    public Logger getChild(final String name) {
        return new Logger(this, name);
    }
    
    protected void log(final String msg) {
        if (this.timestamp) {
            System.out.println(this.timeStamp() + " [" + this.name + "] " + msg);
        }
        else {
            System.out.println("[" + this.name + "] " + msg);
        }
    }
    
    private String timeStamp() {
        if (this.format == null) {
            this.format = new SimpleDateFormat("HH:mm:ss");
        }
        return "[" + this.format.format(new Date()) + "]";
    }
}
