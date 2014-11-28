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

import java.util.ArrayList;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.pexel.commons.BarAPI;
import eu.matejkormuth.pexel.commons.MapData;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.matchmaking.GameState;
import eu.matejkormuth.pexel.commons.minigame.Minigame;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.bukkit.Pexel;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;
import eu.matejkormuth.pexel.slave.bukkit.core.Log;
import eu.matejkormuth.pexel.slave.bukkit.util.NetworkCCFormatter;

/**
 * Arena that has built-in support for pre-game lobby and stuff... Also implements {@link Listener} and calls
 * {@link org.bukkit.plugin.PluginManager#registerEvents(Listener, org.bukkit.plugin.Plugin)} in constructor.
 */
public abstract class AdvancedArena extends AbstractArena implements Listener {
    /**
     * Amount of players, that is required to start the countdown.
     */
    public int      minimumPlayers     = 0;
    /**
     * Lenght of countdown in seconds.
     */
    public int      countdownLenght    = 10;
    /**
     * Location of this arena game spawn.
     */
    public Location gameSpawn;
    /**
     * Specifies if the countdown should be canceled, if a player leaves arena and there is not enough players to start
     * game, but the countdown is alredy running.
     */
    public boolean  countdownCanCancel = true;
    /**
     * Specifies if players can respawn in this arena, or not (default true).
     */
    public boolean  respawnAllowed     = true;
    /**
     * Spcifies if the boss bar should be used for displaying time to start.
     */
    public boolean  useBossBar         = true;
    /**
     * Time left to game start.
     */
    public int      countdownTimeLeft  = this.countdownLenght;
    /**
     * Specifies, if the arena should call <code>reset()</code> function automaticaly when game ends.
     */
    public boolean  autoReset          = true;
    /**
     * Specifies, if inventory actions are enabled in this arena (default: true).
     */
    public boolean  inventoryDisabled  = true;
    /**
     * Chat format for countdown message.
     */
    public String   countdownFormat    = "%timeleft% seconds to game start!";
    // Bukkit task id of countdown.
    protected int   countdownTaskId    = 0;
    /**
     * Identifies if the game has started.
     */
    private boolean gameStarted        = false;
    
    /**
     * @param minigame
     * @param arenaName
     * @param region
     * @param maxPlayers
     */
    public AdvancedArena(final Minigame minigame, final String arenaName,
            final MapData mapData) {
        super(minigame, arenaName, mapData);
        
        this.minimumPlayers = mapData.getOption_Integer(MapData.KEY_MINIMAL_PLAYERS);
        this.countdownLenght = mapData.getOption_Integer(MapData.KEY_COUNTDOWN_LENGHT);
        
        this.gameSpawn = mapData.getLocation(MapData.KEY_ARENA_SPAWN);
        
        NetworkCCFormatter.sendConstructor(this);
        
        // TODO: Registers events in bukkit. Needs to be reworked.
        Bukkit.getPluginManager().registerEvents(this, Pexel.getCore());
    }
    
    /**
     * Clear player's inventory including armor slots.
     * 
     * @param player
     *            player to clear inventory
     */
    public void clearPlayerInventory(final Player player) {
        if (player == null) {
            throw new NullArgumentException("player");
        }
        else {
            //player.getInventory().clear();
            //player.getInventory().setHelmet(null);
            //player.getInventory().setChestplate(null);
            //player.getInventory().setLeggings(null);
            //player.getInventory().setBoots(null);
        }
    }
    
    /**
     * Teleports all players to specified location.
     * 
     * @param location
     *            destination location
     */
    public void teleportPlayers(final Location location) {
        for (Player p : this.activePlayers)
            p.teleport(location);
    }
    
    /**
     * Returns boolean if is this arena prepeared for playing.
     * 
     * @return whether is arena ready for playing
     */
    public boolean isPrepeared() {
        return this.gameSpawn != null && this.minimumPlayers != 0;
    }
    
    /**
     * Tries to start countdown.
     */
    private void tryStartCountdown() {
        if (this.activePlayers.size() >= this.minimumPlayers)
            this.startCountdown();
        else
            this.onNotEnoughPlayers();
    }
    
    /**
     * Tries to stop countdown.
     */
    private void tryStopCountdown() {
        //Check if we can stop, once the countdown started.
        if (this.countdownCanCancel) {
            Pexel.getScheduler().cancelTask(this.countdownTaskId);
            this.onCountdownCancelled();
        }
    }
    
    /**
     * Returns world that this arena exists. (Got from world in this.gameSpawn).
     * 
     * @return world of this arena
     */
    public World getWorld() {
        return this.gameSpawn.getWorld();
    }
    
    private void startCountdown() {
        if (this.countdownTaskId == 0) {
            NetworkCCFormatter.sendCDstart(this);
            //Reset countdown time.
            this.countdownTimeLeft = this.countdownLenght;
            //Start countdown.
            this.countdownTaskId = Pexel.getScheduler().scheduleSyncRepeatingTask(
                    new Runnable() {
                        @Override
                        public void run() {
                            AdvancedArena.this.countdownTick();
                        }
                    }, 0L, 20L);
            
            this.onCountdownStart();
        }
    }
    
    private void onCountdownStop() {
        Pexel.getScheduler().cancelTask(this.countdownTaskId);
        NetworkCCFormatter.sendCDstop(this);
    }
    
    /**
     * Called once per second while countdown is running.
     */
    private void countdownTick() {
        //Send a chat message.
        if (this.countdownTimeLeft < 10 || (this.countdownTimeLeft % 10) == 0) {
            this.chatAll(ChatManager.minigame(
                    this.minigame,
                    this.countdownFormat.replace("%timeleft%",
                            Integer.toString(this.countdownTimeLeft))));
            for (Player p : this.getPlayers()) {
                p.playSound(p.getLocation(), Sound.WOLF_GROWL, 1F, 1F);
            }
        }
        
        //If we are using boss bar.
        if (this.useBossBar) {
            this.setBossBarAll(
                    this.countdownFormat.replace("%timeleft%",
                            Integer.toString(this.countdownTimeLeft)),
                    this.countdownTimeLeft / this.countdownLenght * 100F);
        }
        
        //If we reached zero.
        if (this.countdownTimeLeft <= 0) {
            //Remove bossbar
            if (this.useBossBar)
                for (Player p : this.activePlayers)
                    BarAPI.removeBar(p);
            //Stop the countdown task.
            this.onCountdownStop();
            //Start game.
            this.chatAll(ChatManager.minigame(
                    this.getMinigame(),
                    ChatColor.GREEN + "Map: " + ChatColor.WHITE + this.mapData.getName()
                            + ChatColor.WHITE + " by " + ChatColor.RED
                            + this.mapData.getAuthor()));
            this.onGameStart();
            this.gameStarted = true;
        }
        
        // Set food level.
        for (Player p : this.getPlayers()) {
            p.setFoodLevel(20);
        }
        
        //Decrement the time.
        this.countdownTimeLeft -= 1;
    }
    
    /**
     * Set's boss bar content for all players.
     * 
     * @param replace
     *            message (max 40 char.)
     */
    public void setBossBarAll(final String message) {
        for (Player p : this.activePlayers) {
            BarAPI.removeBar(p);
            BarAPI.setMessage(p, message);
        }
    }
    
    /**
     * Set's boss bar content for all players.
     * 
     * @param replace
     *            message (max 40 char.)
     */
    public void setBossBarAll(final String message, final float percent) {
        for (Player p : this.activePlayers) {
            BarAPI.removeBar(p);
            BarAPI.setMessage(p, message, percent);
        }
    }
    
    /**
     * Reseta arena basic things. <b>Calls {@link AdvancedArena#onReset()} at the end of this function!</b></br> If you
     * want to extend reset function, override onReset() function.
     */
    public final void reset() {
        this.state = GameState.RESETING;
        
        Log.info("Resetting arena " + this.areaName + "...");
        
        for (Player p : new ArrayList<Player>(this.activePlayers)) {
            this.onPlayerLeft(p, DisconnectReason.KICK_BY_SERVER);
        }
        
        NetworkCCFormatter.send(NetworkCCFormatter.MSG_TYPE_ARENA_STATE, this,
                this.state.toString());
        
        this.gameStarted = false;
        this.countdownTaskId = 0;
        //Not many things happeing here. Leaving method for future.
        this.activePlayers.clear();
        //Invoke callback.
        this.onReset();
    }
    
    /**
     * Called right after the arena resets it's basic things, after {@link AdvancedArena#reset()} was called. <b>Don't
     * forget to change arena's state to {@link GameState#WAITING_EMPTY} after reset.</b>
     */
    public void onReset() {
        
    }
    
    /**
     * Called when countdown starts.
     */
    public void onCountdownStart() {
        
    }
    
    /**
     * Called when countdown stops.
     */
    public void onCountdownCancelled() {
        
    }
    
    /**
     * Called each time, a player joins arena and there is not enough players for countdown start.
     */
    public void onNotEnoughPlayers() {
        
    }
    
    /**
     * Called when game should start its logic. Called when lobby countdown has reached zero and there is enough
     * players.
     */
    public void onGameStart() {
        
    }
    
    /**
     * Called when last player lefts the arena. Should call {@link AdvancedArena#reset()} function if
     * <code>autoReset</code> is set to <b>false</b> (false by default).
     */
    public void onGameEnd() {
        
    }
    
    /**
     * Called when players join the arena. Also checks if there are enough players, if so, calls
     * {@link AdvancedArena#onGameStart()}. If not, calls {@link AdvancedArena#onNotEnoughPlayers()}.
     */
    @Override
    public void onPlayerJoin(final Player player) {
        if (!this.activePlayers.contains(player)) {
            super.onPlayerJoin(player);
            
            this.tryStartCountdown();
            
            // If enough players, start the game soon.
            if (this.getPlayerCount() == this.slots) {
                if (this.countdownTimeLeft > 10)
                    this.countdownTimeLeft = 10;
            }
            
            this.updateGameState();
            
            this.clearPlayerInventory(player);
            
            // Remove effects from lobbies.
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.JUMP);
            
            // TODO: Should use template instead of hardcoding message style.
            this.chatAll(ChatManager.minigame(this.getMinigame(),
                    ChatColor.GOLD + "Player '" + player.getName() + "' joined arena! ("
                            + this.getPlayerCount() + "/" + this.minimumPlayers + " - "
                            + this.slots + ")"));
            
            // Teleport player to arena spawn location.
            player.teleport(this.gameSpawn);
        }
        else {
            player.sendMessage(ChatManager.error("Alredy playing!"));
        }
    }
    
    /**
     * Called when player left the arena. If is arena in LOBBY/WAITING_PLAYERS state, and flag
     * {@link AdvancedArena#countdownCanCancel} is set to <b>true</b>, stops the countdown.
     */
    @Override
    public void onPlayerLeft(final Player player, final DisconnectReason reason) {
        super.onPlayerLeft(player, reason);
        
        this.chatAll(ChatManager.minigame(this.getMinigame(),
                "Player '" + player.getName() + "' has left arena (" + reason.name()
                        + ")!"));
        
        this.tryStopCountdown();
        
        this.checkForEnd();
        
        // BarApi fix.
        if (BarAPI.hasBar(player))
            BarAPI.removeBar(player);
        
        // Alway remove from spectating mode.
        if (this.isSpectating(player)) {
            this.setSpectating(player, false);
        }
        
        this.updateGameState();
        
        // Clear player's inventory.
        this.clearPlayerInventory(player);
    }
    
    @EventHandler
    protected void onPlayerQuit(final PlayerQuitEvent event) {
        Player p = PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(event.getPlayer().getUniqueId());
        if (this.contains(p))
            this.onPlayerLeft(p, DisconnectReason.PLAYER_DISCONNECT);
    }
    
    /**
     * Updates game state.
     */
    private void updateGameState() {
        if (!this.gameStarted) {
            if (this.getPlayerCount() == 0)
                this.state = GameState.WAITING_EMPTY;
            else
                this.state = GameState.WAITING_PLAYERS;
        }
    }
    
    /**
     * Checks if there are no players in arena, and if arena is in PLAYING state. If so, the
     * {@link AdvancedArena#onGameEnd()}
     */
    private void checkForEnd() {
        if (this.activePlayers.size() <= 1 && this.state.isPlaying()) {
            this.onGameEnd();
            if (this.autoReset)
                this.reset();
        }
    }
    
    @EventHandler
    public void ___onPlayerRespawn(final PlayerRespawnEvent event) {
        Player p = PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(event.getPlayer().getUniqueId());
        if (this.contains(p))
            if (!this.respawnAllowed) {
                //Kick from arena
                this.onPlayerLeft(p, DisconnectReason.LEAVE_BY_GAME);
            }
    }
    
    @EventHandler
    public void onPlayerInventoryClick(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (this.activePlayers.contains(PexelSlave.getInstance()
                    .getObjectFactory()
                    .getPlayer(event.getWhoClicked()))) {
                if (this.inventoryDisabled) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    public int getMinimalPlayers() {
        return this.minimumPlayers;
    }
    
    public void setMinimalPlayers(final int minimalPlayers) {
        this.minimumPlayers = minimalPlayers;
    }
    
    public int getCountdownLenght() {
        return this.countdownLenght;
    }
    
    public void setCountdownLenght(final int countdownLenght) {
        this.countdownLenght = countdownLenght;
    }
    
    /**
     * @deprecated Deprecated and removed, now returns only {@link Pexel#getHubLocation()}.
     * @return
     */
    @Deprecated
    public Location getLobbyLocation() {
        return Pexel.getHubLocation();
    }
    
    /**
     * Does nothing now.
     * 
     * @deprecated Deprecated and removed. see {@link AdvancedArena#getLobbyLocation()} for explanamination.
     * @param lobbyLocation
     */
    @Deprecated
    public void setLobbyLocation(final Location lobbyLocation) {
        //this.lobbyLocation = lobbyLocation;
    }
    
    public void setGameState(final GameState state) {
        this.state = state;
    }
    
    public Location getGameSpawn() {
        return this.gameSpawn;
    }
    
    public void setGameSpawn(final Location gameSpawn) {
        this.gameSpawn = gameSpawn;
    }
    
    public boolean countdownCanCancel() {
        return this.countdownCanCancel;
    }
    
    public void setCountdownCanCancel(final boolean countdownCanCancel) {
        this.countdownCanCancel = countdownCanCancel;
    }
    
    public boolean playersCanRespawn() {
        return this.respawnAllowed;
    }
    
    public void setPlayersCanRespawn(final boolean playersCanRespawn) {
        this.respawnAllowed = playersCanRespawn;
    }
    
    public int getCountdownTimeLeft() {
        return this.countdownTimeLeft;
    }
    
    public String getCountdownFormat() {
        return this.countdownFormat;
    }
    
    public void setCountdownFormat(final String countdownFormat) {
        this.countdownFormat = countdownFormat;
    }
}
