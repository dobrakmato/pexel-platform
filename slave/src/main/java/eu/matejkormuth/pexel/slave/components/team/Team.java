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
package eu.matejkormuth.pexel.slave.components.team;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.PlayerHolder;
import eu.matejkormuth.pexel.commons.chat.ChatChannel;
import eu.matejkormuth.pexel.commons.chat.PlayerChannelSubscriber;
import eu.matejkormuth.pexel.commons.chat.SubscribeMode;
import eu.matejkormuth.pexel.commons.text.ChatColor;
import eu.matejkormuth.pexel.slave.PexelSlave;

/**
 * Class that represetns a team.
 */
public class Team implements PlayerHolder {
    /**
     * List of player in team.
     */
    private final List<Player> players  = new ArrayList<Player>();
    /**
     * Color of team.
     */
    private final Color        color;
    /**
     * Name of team.
     */
    private final String       name;
    /**
     * Chat channel of the team.
     */
    private final ChatChannel  teamchat = ChatChannel.createRandom();
    /**
     * Maximum amount of players in this team.
     */
    private final int          maxPlayers;
    
    /**
     * Creates a new Team with specified team color and team name.
     * 
     * @param color
     *            color of team
     * @param name
     *            name of team
     * @param maximumPlayers
     *            maximum amount of players in team
     */
    public Team(final Color color, final String name, final int maximumPlayers) {
        this.color = color;
        this.name = name;
        this.maxPlayers = maximumPlayers;
        this.teamchat.setPrefix(ChatColor.YELLOW + "[TEAM] ");
    }
    
    /**
     * Teleports all players.
     * 
     * @param loc
     *            location, to teleport to players
     */
    public void teleportAll(final Location loc) {
        for (Player p : this.players)
            p.teleport(loc);
    }
    
    /**
     * Applies dyed armor to all players.
     */
    public void applyArmorAll() {
        for (Player p : this.players)
            this.applyArmor(p);
    }
    
    /**
     * Adds player to team.
     * 
     * @param p
     *            player to add
     */
    public void addPlayer(final Player p) {
        p.sendMessage(ChatColor.GREEN + "You have joined team '" + this.name + "'!");
        this.players.add(p);
        this.teamchat.subscribe(new PlayerChannelSubscriber(PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(p), SubscribeMode.READ_WRITE));
    }
    
    /**
     * Adds players to team.
     * 
     * @param players
     *            players to add
     */
    public void addPlayers(final Player... players) {
        for (Player p : players)
            this.addPlayer(p);
    }
    
    /**
     * Removes player from the team.
     * 
     * @param p
     *            player to remove
     */
    public void removePlayer(final Player p) {
        p.sendMessage(ChatColor.GREEN + "You have left team '" + this.name + "'!");
        this.players.remove(p);
        this.teamchat.unsubscribe(p);
    }
    
    /**
     * Return's this team chat channel.
     * 
     * @return team chat channel
     */
    public ChatChannel getTeamChannel() {
        return this.teamchat;
    }
    
    /**
     * Returns name of the team.
     * 
     * @return name of team
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Applies armor to specified player.
     * 
     * @param player
     *            player to apply armor to
     */
    public void applyArmor(final Player player) {
        // TODO: Apply armor
        //player.getInventory().setHelmet(
        //        ItemUtils.coloredLetherArmor(Material.LEATHER_HELMET, this.color));
        //player.getInventory().setChestplate(
        //        ItemUtils.coloredLetherArmor(Material.LEATHER_CHESTPLATE, this.color));
        //player.getInventory().setLeggings(
        //        ItemUtils.coloredLetherArmor(Material.LEATHER_LEGGINGS, this.color));
        //player.getInventory().setBoots(
        //        ItemUtils.coloredLetherArmor(Material.LEATHER_BOOTS, this.color));
    }
    
    /**
     * Retruns list of players.
     * 
     * @return list of players in this team
     */
    @Override
    public List<Player> getPlayers() {
        return this.players;
    }
    
    /**
     * Returns if this team contains specified player.
     * 
     * @param p
     *            specified player
     * @return true or false
     */
    @Override
    public boolean contains(final Player p) {
        return this.players.contains(p);
    }
    
    /**
     * Returns player count.
     * 
     * @return amount of players
     */
    @Override
    public int getPlayerCount() {
        return this.players.size();
    }
    
    /**
     * Get maximum number of players in team.
     * 
     * @return amount of players
     */
    public int getMaximumPlayers() {
        return this.maxPlayers;
    }
    
    /**
     * Returns whether one player can join this team.
     * 
     * @return boolean, if player can join
     */
    public boolean canJoin() {
        return this.players.size() < this.maxPlayers;
    }
    
    /**
     * Returns whether specified amount of players can join this team.
     * 
     * @param amount
     *            amount of players
     * @return true, if they can, else false
     */
    public boolean canJoin(final int amount) {
        return this.players.size() + amount < this.maxPlayers;
    }
    
    /**
     * Retrun's this team color.
     * 
     * @return color of team
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * Applies colored armor to specified player.
     * 
     * @param p
     *            specified player
     */
    public void applyArmorTo(final Player p) {
        this.applyArmor(p);
    }
}
