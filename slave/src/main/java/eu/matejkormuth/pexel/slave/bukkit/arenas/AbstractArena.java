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
package eu.matejkormuth.pexel.slave.bukkit.arenas;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eu.matejkormuth.pexel.commons.MapData;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.PlayerHolder;
import eu.matejkormuth.pexel.commons.arenas.ArenaState;
import eu.matejkormuth.pexel.commons.arenas.LeaveReason;
import eu.matejkormuth.pexel.commons.bans.Bannable;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.commons.minigame.Minigame;
import eu.matejkormuth.pexel.slave.bukkit.areas.ProtectedArea;
import eu.matejkormuth.pexel.slave.bukkit.core.Log;
import eu.matejkormuth.pexel.slave.bukkit.core.StorageEngine;
import eu.matejkormuth.pexel.slave.bukkit.core.ValidityChecker;
import eu.matejkormuth.pexel.slave.bukkit.util.ItemUtils;

/**
 * Abstract minigame arena - {@link ProtectedArea} that implements {@link MatchmakingGame}, {@link PlayerHolder},
 * {@link Bannable}. Contains some useful functions and basic strucutre of minigame.
 */
// TODO: Rewrite.
public abstract class AbstractArena extends ProtectedArea implements MatchmakingGame,
        PlayerHolder, Bannable {
    /**
     * Number of actual maximum number of players. May be changed at runtime, so it does not reflect max. player from
     * {@link MapData}.
     */
    protected int                slots;
    /**
     * The actual state of the arena.
     */
    protected ArenaState         state             = ArenaState.WAITING_PLAYERS;
    /**
     * The game mode that players should get, when they join the game.
     */
    protected GameMode           defaultGameMode   = GameMode.ADVENTURE;
    /**
     * List of active players in arena.
     */
    protected final List<Player> activePlayers     = new ArrayList<Player>();
    /**
     * List of spectating players in arena.
     */
    protected final List<Player> spectatingPlayers = new ArrayList<Player>();
    /**
     * Reference to minigame.
     */
    protected final Minigame     minigame;
    /**
     * {@link MapData} that is currenlty played on this arena.
     */
    protected MapData            mapData;
    
    public AbstractArena(final Minigame minigame, final String arenaName,
            final MapData mapData) {
        super(minigame.getName() + "_" + arenaName, mapData.getProtectedRegion());
        this.minigame = minigame;
        this.slots = mapData.getMaxPlayers();
        this.mapData = mapData;
        
        // Check MapData validity.
        ValidityChecker.checkMapData(mapData);
    }
    
    /**
     * Sends a chat message to all player in arena.
     * 
     * @param msg
     *            message to be send
     */
    public void chatAll(final String msg) {
        for (Player p : this.activePlayers) {
            p.sendMessage(msg);
        }
    }
    
    @Override
    public int getFreeSlots() {
        return this.slots - this.activePlayers.size();
    }
    
    @Override
    public int getMaximumSlots() {
        return this.slots;
    }
    
    @Override
    public ArenaState getState() {
        return this.state;
    }
    
    @Override
    public List<Player> getPlayers() {
        return this.activePlayers;
    }
    
    @Override
    public boolean canJoin() {
        return this.getFreeSlots() >= 1
                && (this.state == ArenaState.WAITING_PLAYERS
                        || this.state == ArenaState.WAITING_EMPTY || this.state == ArenaState.PLAYING_CANJOIN);
    }
    
    @Override
    public boolean canJoin(final int count) {
        return this.getFreeSlots() >= count
                && (this.state == ArenaState.WAITING_PLAYERS
                        || this.state == ArenaState.WAITING_EMPTY || this.state == ArenaState.PLAYING_CANJOIN);
    }
    
    public void onPlayerJoin(final Player player) {
        //if (Pexel.getBans().isBanned(player, this)) {
        //    player.sendMessage(ChatManager.error(BanUtils.formatBannedMessage(Pexel.getBans()
        //            .getBan(player, this))));
        //}
        //else {
        this.activePlayers.add(player);
        player.setGameMode(eu.matejkormuth.pexel.commons.GameMode.valueOf(this.defaultGameMode.name()));
        //}
    }
    
    public void onPlayerLeft(final Player player, final DisconnectReason reason) {
        this.activePlayers.remove(player);
    }
    
    /**
     * Plays sound for all players in arena.
     * 
     * @param sound
     *            sound to play
     * @param volume
     *            volume
     * @param pitch
     *            pitch
     */
    public void playSoundAll(final Sound sound, final float volume, final float pitch) {
        for (Player p : this.activePlayers)
            p.playSound(p.getLocation(), sound, volume, pitch);
    }
    
    /**
     * Sets spectating mode for player in this arena.
     * 
     * @param player
     *            player
     * @param spectating
     *            the value if the player should be spectating or not.
     */
    public void setSpectating(final Player player, final boolean spectating) {
        if (spectating) {
            if (!StorageEngine.getProfile(player.getUniqueId()).isSpectating()) {
                player.sendMessage("You are now spectating!");
                StorageEngine.getProfile(player.getUniqueId()).setSpectating(true);
                player.getInventory().clear();
                player.getInventory().addItem(
                        ItemUtils.namedItemStack(Material.COMPASS, ChatColor.YELLOW
                                + "Spectating", null));
                player.setGameMode(eu.matejkormuth.pexel.commons.GameMode.ADVENTURE);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,
                        Integer.MAX_VALUE, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
                        Integer.MAX_VALUE, 0));
                player.setAllowFlight(true);
                player.setFlying(true);
                this.spectatingPlayers.add(player);
            }
            else {
                //Player is already spectating.
                Log.warn("Player '" + player.getName()
                        + "' can't be moved to spectating mode by game '"
                        + this.getMinigame().getName()
                        + "': Player is already in spectating mode!");
            }
        }
        else {
            if (StorageEngine.getProfile(player.getUniqueId()).isSpectating()) {
                player.sendMessage("You are no longer spectating!");
                StorageEngine.getProfile(player.getUniqueId()).setSpectating(false);
                player.getInventory().clear();
                player.setGameMode(eu.matejkormuth.pexel.commons.GameMode.valueOf(this.defaultGameMode.name()));
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.setAllowFlight(false);
                player.setFlying(false);
                this.spectatingPlayers.remove(player);
            }
            else {
                //Player is not spectating.
                Log.warn("Player '" + player.getName()
                        + "' can't be moved from spectating mode by game '"
                        + this.getMinigame().getName()
                        + "': Player is not in spectating mode!");
            }
        }
    }
    
    /**
     * Returns whether is specified player in spectator mode or not.
     * 
     * @param player
     *            player to check
     * @return true or false
     */
    public boolean isSpectating(final Player player) {
        return StorageEngine.getProfile(player.getUniqueId()).isSpectating() || false;
    }
    
    /**
     * Returns whether is arena empty.
     * 
     * @return true if arena is empty
     */
    @Override
    public boolean empty() {
        return this.activePlayers.size() == 0;
    }
    
    /**
     * Kicks all players from arena. Uses KICK_BY_GAME as {@link LeaveReason}
     */
    public void kickAll() {
        // Iteration problem, pls fix. - fixed.
        for (Player p : new ArrayList<Player>(this.activePlayers)) {
            this.onPlayerLeft(p, DisconnectReason.LEAVE_BY_GAME);
        }
    }
    
    /**
     * Sends a message to all players and kicks them. Uses KICK_BY_GAME as {@link LeaveReason}
     * 
     * @param message
     *            message to send
     */
    public void kickAll(final String message) {
        // Iteration problem, pls fix. -31.10.2014 fixed /mato
        for (Player p : new ArrayList<Player>(this.activePlayers)) {
            p.sendMessage(message);
            this.onPlayerLeft(p, DisconnectReason.LEAVE_BY_GAME);
        }
    }
    
    /**
     * Return minigame that is played in this arena.
     * 
     * @return the minigame
     */
    public Minigame getMinigame() {
        return this.minigame;
    }
    
    /**
     * @deprecated Use {@link MapData} and its saving / loading for saving or loading arena data.
     * @param path
     */
    @Deprecated
    public void save(final String path) {
        try {
            Document conf = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = conf.createElement("aconfig");
            conf.appendChild(root);
            
            Element info = conf.createElement("info");
            
            root.appendChild(info);
            
            Element options = conf.createElement("options");
            
            for (Field f : this.getClass().getDeclaredFields())
                if (f.isAnnotationPresent(ArenaOption.class)) {
                    ArenaOption annotation = f.getAnnotation(ArenaOption.class);
                    Element option = conf.createElement("option");
                    option.setAttribute("name", f.getName());
                    option.setAttribute("type", f.getType().getCanonicalName());
                    option.setAttribute("annotation", annotation.name());
                    option.setAttribute("persistent",
                            Boolean.toString(annotation.persistent()));
                    
                    if (!f.isAccessible())
                        f.setAccessible(true);
                    
                    if (f.getType().equals(Location.class)) {
                        f.setAccessible(true);
                        option.setTextContent(f.get(this).toString());
                    }
                    else {
                        option.setTextContent(f.get(this).toString());
                    }
                    
                    options.appendChild(option);
                }
            
            root.appendChild(options);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(conf);
            StreamResult result = new StreamResult(new File(path));
            
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set's number of slots at runtime. Does not reflect / or change value in {@link MapData} config.
     * 
     * @param slots
     *            new number of slots
     */
    public void setSlots(final int slots) {
        this.slots = slots;
    }
    
    /**
     * Set's current state to specified state.
     * 
     * @param stateToSet
     */
    public void setState(final ArenaState stateToSet) {
        this.state = stateToSet;
    }
    
    @Override
    public int getPlayerCount() {
        return this.activePlayers.size();
    }
    
    @Override
    public boolean contains(final Player player) {
        return this.activePlayers.contains(player);
    }
    
    @Override
    public String getBannableID() {
        return "MA-" + this.areaName;
    }
    
    @Override
    public String getBannableName() {
        return this.getMinigame().getDisplayName() + " - " + this.areaName;
    }
}
