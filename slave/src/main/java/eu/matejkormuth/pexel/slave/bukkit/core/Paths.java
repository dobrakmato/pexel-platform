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

import java.util.UUID;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * Class used for generating paths.
 * 
 * @author Mato Kormuth
 * 
 */
public class Paths {
    /**
     * Returns path for player profile.
     * 
     * @param uuid
     * @return
     */
    public static final String playerProfile(final UUID uuid) {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/profiles/"
                + uuid.toString() + ".yml";
    }
    
    public static String lobbiesPath() {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/lobbies.yml";
    }
    
    public static String arenasPath() {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/arenas.yml";
    }
    
    public static String gatesPath() {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/gates.yml";
    }
    
    public static String msuCache() {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/msu.cache";
    }
    
    public static String matchRecord(final String name) {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/records/" + name
                + ".record";
    }
    
    public static String arenaPath(final String name) {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/arenas/" + name
                + ".xml";
    }
    
    public static String profilePath(final String name) {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/profiles/" + name
                + ".xml";
    }
    
    public static String cache(final String name) {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/cache/" + name
                + ".cache";
    }
    
    public static String clips() {
        return Pexel.getCore().getDataFolder().getAbsolutePath() + "/clips/";
    }
}
