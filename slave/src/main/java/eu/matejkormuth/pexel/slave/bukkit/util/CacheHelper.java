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
package eu.matejkormuth.pexel.slave.bukkit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import eu.matejkormuth.pexel.slave.bukkit.core.Log;
import eu.matejkormuth.pexel.slave.bukkit.core.Paths;

/**
 * Class used for caching data.
 */
public class CacheHelper {
    private Map<String, Object> cache = new HashMap<>();
    private final String        name;
    private boolean             nr    = false;
    
    public CacheHelper(final String name) {
        this.name = name;
        try {
            this.load();
        } catch (ClassNotFoundException | IOException e) {
            this.nr = true;
            Log.severe("Cache broken!");
            e.printStackTrace();
        }
    }
    
    public boolean needsRebuild() {
        return this.nr;
    }
    
    @SuppressWarnings("unchecked")
    private void load() throws FileNotFoundException, IOException,
            ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(
                Paths.cache(this.name))));
        Object object = ois.readObject();
        ois.close();
        if (object instanceof HashMap<?, ?>)
            this.cache = (HashMap<String, Object>) object;
        else
            throw new RuntimeException("Broken cache file! Can't read data!");
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSize() {
        return (int) new File(Paths.cache(this.name)).length();
    }
    
    public void clear() {
        this.cache.clear();
    }
    
    public void put(final String key, final Object value) {
        this.cache.put(key, value);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getTyped(final String key) {
        return (T) this.cache.get(key);
    }
    
    public Object get(final String key) {
        return this.cache.get(key);
    }
    
    public void commit() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(
                Paths.cache(this.name))));
        oos.writeObject(this.cache);
        oos.close();
    }
    
}
