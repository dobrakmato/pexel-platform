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
package eu.matejkormuth.pexel.slave.pluginloaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;

import eu.matejkormuth.pexel.commons.Logger;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.SlaveComponent;

/**
 * Standart slave component type plugin loader.
 */
public class SlaveComponentLoader {
    private final Logger log;
    
    public SlaveComponentLoader(final Logger log) {
        this.log = log.getChild("ComponentLoader");
    }
    
    public void loadAll(final File folder) {
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".jar")) {
                try {
                    this.load(file);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void load(final File file) throws IOException, ClassNotFoundException {
        this.addURL(file.toURI().toURL());
        InputStream in = null;
        URL inputURL = null;
        String inputFile = "jar:file:/" + file.getAbsolutePath() + "!/plugin.json";
        if (inputFile.startsWith("jar:")) {
            try {
                inputURL = new URL(inputFile);
                JarURLConnection conn = (JarURLConnection) inputURL.openConnection();
                in = conn.getInputStream();
            } catch (MalformedURLException e1) {
                System.err.println("Malformed input URL: " + inputURL);
                return;
            }
            JsonElement element = Streams.parse(new JsonReader(new InputStreamReader(in)));
            JsonObject obj = element.getAsJsonObject();
            this.log.info("Enabling " + obj.get("name").getAsString() + "...");
            Class<?> clazz = Class.forName(obj.get("slaveComponentClass").getAsString());
            if (SlaveComponent.class.isAssignableFrom(clazz)) {
                try {
                    Object component = clazz.getConstructor().newInstance();
                    PexelSlave.getInstance().addComponent((SlaveComponent) component);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addURL(final URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;
        
        try {
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] { u });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }
        
    }
}
