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
package eu.matejkormuth.pexel.commons.arenas;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.ChatColor;
import eu.matejkormuth.pexel.commons.GameMode;
import eu.matejkormuth.pexel.commons.MapData;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.TextTable;
import eu.matejkormuth.pexel.commons.bans.Bannable;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.commons.minigame.Minigame;

/**
 * Represents minigame arena, that is participating in matchmaking,
 */
public abstract class Arena extends ProtectedArea implements MatchmakingGame, Bannable {
    // Minimum amount of players required to start countdown.
    public static final float MIN_RATIO              = 0.75F;
    // Player in arena.
    private final Set<Player> players                = new HashSet<Player>();
    // State of arena.
    private ArenaState        state;
    // Maximum amount of players in this arena.
    private final int         maxPlayers             = 16;
    
    private final long        countdownLenght        = 60;
    // Current remaining time of countdown.
    private long              countdownRemaining     = this.countdownLenght;
    // Whether to use boss bar for countdown.
    private final boolean     useBossBar             = true;
    // Whether the game has started or not.
    private boolean           gameStarted            = false;
    
    // Whether this arena is in competitive mode.
    private boolean           competitiveModeEnabled = false;
    
    //Random UUID - gameID.
    private final UUID        gameId                 = UUID.randomUUID();
    
    /**
     * Currently played map in this arena.
     */
    protected MapData         map;
    
    public void join(final Player player) {
        this.broadcast("-> " + player.getDisplayName());
        // Clear player first.
        this.clearPlayer(player);
        
        this.players.add(player);
        
        if (this.competitiveModeEnabled) {
            player.sendMessage("Warning: This arena is in competitive mode! That means you are commiting yourself to play full match, and don't disconnect in middle. Leaving this type of match WILL BE penalized!");
        }
        
        // Update waiting state.
        this.setState(ArenaState.WAITING_PLAYERS);
        
        // Fire event.
        this.onPlayerJoin(player);
        // Try to start countdown.
        this.startCountdown();
    }
    
    public void leave(final Player player, final LeaveReason reason) {
        this.broadcast("<- " + player.getDisplayName());
        
        this.players.remove(player);
        if (this.gameStarted
                && this.competitiveModeEnabled
                && (reason == LeaveReason.PLAYER_LEAVE || reason == LeaveReason.PLAYER_DISCONNECT)) {
            player.sendMessage("You left competitive match before it's end! You will be penaized for that!");
            // TODO: Penalize player.
        }
        
        if (this.empty()) {
            // Update waiting state.
            this.setState(ArenaState.WAITING_EMPTY);
        }
        
        // Fire event.
        this.onPlayerLeave(player, reason);
        // Try to stop countdown.
        this.stopCountdown();
    }
    
    /**
     * Cleares (removes gamemode, flying, potions, armor and items) player.
     * 
     * @param player
     *            player to be cleared
     */
    protected void clearPlayer(final Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setHealth(20D);
        player.setFoodLevel(20);
        // TODO: Armor & potions.
    }
    
    // Tries to start countdown.
    private void startCountdown() {
        if (this.getPlayerCount() >= MIN_RATIO * this.maxPlayers) {
            this.startCountdown0();
        }
    }
    
    // Tries to stop countdown.
    private void stopCountdown() {
        if (this.getPlayerCount() < MIN_RATIO * this.maxPlayers) {
            this.resetCountdown();
        }
    }
    
    // Actually start countdown.
    protected abstract void startCountdown0();
    
    // Actaully stop countdown.
    protected abstract void stopCountdown0();
    
    // Actually stops and resets countdown.
    private void resetCountdown() {
        this.stopCountdown0();
        this.countdownRemaining = this.countdownLenght;
    }
    
    /**
     * Called each second, updates boss bars, chat + start games when countdown reaches zero.
     */
    protected void doCountdown() {
        // Lower remaining time.
        this.countdownRemaining--;
        
        //Countdown in boss bar.
        for (Player p : this.players) {
            if (this.useBossBar) {
                p.setBossBar(
                        this.countdownRemaining + " seconds to start!",
                        ((float) this.countdownRemaining / (float) this.countdownLenght * 100F));
            }
        }
        
        // Countdown in chat.
        if (this.countdownRemaining < 10L) {
            this.broadcast(this.countdownRemaining
                    + " seconds to game start! Get ready!");
        }
        
        // If we reached zero, start that game!
        if (this.countdownRemaining <= 0) {
            this.resetCountdown();
            
            // Build table.
            TextTable table = new TextTable(TextTable.MINECRAFT_CHAT_WIDTH, 5);
            table.renderLeftAlignedText(1, "Game: "
                    + this.getMinigame().getDisplayName());
            table.renderLeftAlignedText(2, "Map: " + this.map.getName() + " by "
                    + this.map.getAuthor());
            table.formatBorder(ChatColor.YELLOW);
            
            this.broadcast(table.toString());
            
            this.gameStarted = true;
            
            this.setState(ArenaState.PLAYING_CANTJOIN);
            this.onGameStart();
        }
    }
    
    /**
     * Returns minigame information.
     * 
     * @return minigame object
     */
    public abstract Minigame getMinigame();
    
    /**
     * Reset's this arena.
     */
    public void reset() {
        this.gameStarted = false;
        
        this.onReset();
        this.setState(ArenaState.WAITING_EMPTY);
    }
    
    /**
     * Sets this arena's state.
     * 
     * @param state
     */
    public void setState(final ArenaState state) {
        this.state = state;
    }
    
    /**
     * Broadcasts message to player in this arena.
     * 
     * @param message
     *            message to be broadcasted
     */
    public void broadcast(final String message) {
        for (Player player : this.players) {
            player.sendMessage(message);
        }
    }
    
    /**
     * Called when countdown reaches zero and game is about to start.
     */
    protected abstract void onGameStart();
    
    /**
     * Called when arena should be resetted to it's default state and prepeare for next match. Triggered automatically,
     * when all the players are disconnected / kicked from arena.
     */
    protected abstract void onReset();
    
    /**
     * Called when player joins this arena.
     * 
     * @param player
     *            player that joined
     */
    protected abstract void onPlayerJoin(Player player);
    
    /**
     * Called when player leaves this arena with specified reason of leave.
     * 
     * @param player
     *            player that left
     * @param reason
     *            leave reason
     */
    protected abstract void onPlayerLeave(Player player, LeaveReason reason);
    
    @Override
    public ArenaState getState() {
        return this.state;
    }
    
    @Override
    public boolean empty() {
        return this.players.size() == 0;
    }
    
    @Override
    public int getPlayerCount() {
        return this.players.size();
    }
    
    @Override
    public boolean canJoin() {
        return this.canJoin(1);
    }
    
    @Override
    public boolean canJoin(final int count) {
        return this.getFreeSlots() >= count;
    }
    
    @Override
    public int getMaximumSlots() {
        return this.maxPlayers;
    }
    
    @Override
    public int getFreeSlots() {
        return this.maxPlayers - this.players.size();
    }
    
    @Override
    public boolean contains(final Player player) {
        return this.players.contains(player);
    }
    
    @Override
    public Collection<Player> getPlayers() {
        return new HashSet<Player>(this.players);
    }
    
    public boolean isCompetitiveModeEnabled() {
        return this.competitiveModeEnabled;
    }
    
    public void setCompetitiveModeEnabled(final boolean competitiveModeEnabled) {
        this.competitiveModeEnabled = competitiveModeEnabled;
        if (competitiveModeEnabled) {
            this.broadcast("Warning, competitive mode is now enabled! Each disconnect before game's end will be penalized!");
        }
        else {
            this.broadcast("Competitive mode is now disabled.");
        }
    }
    
    public UUID getUUID() {
        return this.gameId;
    }
}
