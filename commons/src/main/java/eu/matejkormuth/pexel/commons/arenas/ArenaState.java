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

/**
 * Specifies minigame's current state.
 */
public enum ArenaState {
    /**
     * Game state when game is operational and is not empty and it's avaiting more players to start game.
     */
    WAITING_PLAYERS(false, true),
    /**
     * Game state when game is operational but it's empty.
     */
    WAITING_EMPTY(false, true),
    /**
     * Game state, when game is runnning, but players can join in mid-game.
     */
    PLAYING_CANJOIN(true, true),
    /**
     * Game sate, when game is running, but no more players can join the running game.
     */
    PLAYING_CANTJOIN(true, false),
    /**
     * Game state, when game is reseting it's arena, thus is not operational.
     */
    RESETING(false, false),
    /**
     * Game state, when game is not operational at all.
     */
    DISABLED(false, false);
    
    private boolean playing;
    private boolean canjoin;
    
    private ArenaState(final boolean playing, final boolean canjoin) {
        this.playing = playing;
        this.canjoin = canjoin;
    }
    
    /**
     * Returns whether this state menas that arena is in playing state.
     * 
     * @return true or false
     */
    public boolean isPlaying() {
        return this.playing;
    }
    
    /**
     * Returns if this state allows joining players.
     * 
     * @return true or false
     */
    public boolean canJoin() {
        return this.canjoin;
    }
}
