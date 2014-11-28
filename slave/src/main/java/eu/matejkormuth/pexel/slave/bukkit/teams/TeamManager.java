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
package eu.matejkormuth.pexel.slave.bukkit.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.slave.PexelSlave;
import eu.matejkormuth.pexel.slave.boot.PexelSlaveBukkitPlugin;
import eu.matejkormuth.pexel.slave.bukkit.arenas.AbstractArena;
import eu.matejkormuth.pexel.slave.bukkit.chat.ChatManager;

/**
 * Class used for managing teams.
 * 
 * @author Mato Kormuth
 * 
 */
public class TeamManager implements Listener {
    /**
     * List of teams
     */
    private final List<Team>          teams    = new ArrayList<Team>();
    /**
     * Caching of sign locations.
     */
    private final Map<Location, Team> signs    = new HashMap<Location, Team>();
    private final int                 varience = 1;
    private final AbstractArena       arena;
    
    /**
     * Creates new Team manager
     * 
     * @param arena
     *            arena in which team manager runs.
     */
    public TeamManager(final AbstractArena arena) {
        Bukkit.getPluginManager().registerEvents(this,
                PexelSlaveBukkitPlugin.getInstance());
        this.arena = arena;
    }
    
    /**
     * Resets team manager.
     */
    public void reset() {
        this.teams.clear();
        for (Location loc : this.signs.keySet())
            this.updateSign(loc, this.signs.get(loc));
        this.signs.clear();
    }
    
    /**
     * Adds team to this manager.
     * 
     * @param team
     *            team to add
     */
    public void addTeam(final Team team) {
        this.teams.add(team);
    }
    
    private void updateSignCache(final Team team) {
        for (Location loc : this.signs.keySet())
            if (this.signs.get(loc) == team)
                this.updateSign(loc, team);
    }
    
    /**
     * Updates sign content on specified location with specified team infromation.
     * 
     * @param location
     *            location of sign
     * @param team
     *            team
     */
    public void updateSign(final Location location, final Team team) {
        this.signs.put(location, team);
        Sign s = (Sign) location.getBlock().getState();
        s.setLine(1, team.getName());
        if (team.getMaximumPlayers() == team.getPlayerCount())
            s.setLine(
                    2,
                    ChatColor.RED.toString() + team.getPlayerCount() + "/"
                            + team.getMaximumPlayers() + " players");
        else if (team.getPlayerCount() / team.getMaximumPlayers() > 0.75F)
            s.setLine(
                    2,
                    ChatColor.GOLD.toString() + team.getPlayerCount() + "/"
                            + team.getMaximumPlayers() + " players");
        else
            s.setLine(
                    2,
                    ChatColor.GREEN.toString() + team.getPlayerCount() + "/"
                            + team.getMaximumPlayers() + " players");
        s.update();
    }
    
    @EventHandler
    private void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK
                || event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)
            if (event.getClickedBlock().getType() == Material.SIGN_POST
                    || event.getClickedBlock().getType() == Material.SIGN) {
                Player p = PexelSlave.getInstance()
                        .getObjectFactory()
                        .getPlayer(event.getPlayer());
                if (this.arena.getPlayers().contains(p)) {
                    this.signClick(p, event.getClickedBlock());
                }
            }
        
    }
    
    @EventHandler
    private void onPlayerLeft(final PlayerQuitEvent event) {
        eu.matejkormuth.pexel.commons.Player p = PexelSlave.getInstance()
                .getObjectFactory()
                .getPlayer(event.getPlayer());
        for (Team t : this.teams) {
            if (t.contains(p)) {
                t.removePlayer(p);
            }
        }
    }
    
    private void signClick(final Player player, final Block clickedBlock) {
        System.out.println("signClick: p: " + player.getName() + ", cb: "
                + clickedBlock.getLocation().toVector().toString());
        Sign s = (Sign) clickedBlock.getState();
        String teamName = s.getLine(1);
        
        if (s.getLine(0).contains("[Team]")) {
            Team team = null;
            for (Team t : this.teams)
                if (t.getName().equalsIgnoreCase(teamName))
                    team = t;
            if (team == null)
                throw new RuntimeException("Team not found in TeamManager: " + teamName);
            
            if (team.getPlayerCount() == team.getMaximumPlayers())
                player.sendMessage(ChatManager.error("This team is full!"));
            else {
                if (team.canJoin()) {
                    if (this.playerInTeam(player)) {
                        Team oldTeam = this.getTeam(player);
                        oldTeam.removePlayer(player);
                        this.updateSignCache(oldTeam);
                    }
                    
                    team.addPlayer(player);
                    team.applyArmor(player);
                    this.updateSign(clickedBlock.getLocation(), team);
                }
                else {
                    player.sendMessage(ChatManager.error("You can't join this team at this time!"));
                }
            }
        }
    }
    
    /**
     * Returns team by player from this team manager.
     * 
     * @param player
     *            player to find
     * @return team, that specified players is in
     */
    public Team getTeam(final Player player) {
        for (Team t : this.teams)
            if (t.contains(player))
                return t;
        return null;
    }
    
    /**
     * Return whether specified player is in team, manager by this manager.
     * 
     * @param player
     *            player to find
     * @return true or false
     */
    public boolean playerInTeam(final Player player) {
        for (Team t : this.teams)
            if (t.contains(player))
                return true;
        return false;
    }
    
    /**
     * Returns if specified player can join specified team at this time.
     * 
     * @param team
     *            team player whats to join
     * @param player
     *            specififed player
     * @return true or false
     */
    public boolean canJoinTeam(final Team team, final Player player) {
        return team.getPlayerCount() > this.getAvarangePlayerCount();
    }
    
    /**
     * Returns avarange player count in teams of this manager.
     * 
     * @return avarange player count
     */
    public int getAvarangePlayerCount() {
        int allPlayers = this.varience;
        for (Team team : this.teams)
            allPlayers += team.getPlayerCount();
        return (int) ((allPlayers / this.teams.size()) + 0.5);
    }
    
    /**
     * Automatically joins team for specified player.
     * 
     * @param p
     *            player, that have no team.
     * @return team, that player joined
     */
    public Team autoJoinTeam(final Player p) {
        Team leastCrowdedTeam = this.teams.get(0);
        
        for (Team t : this.teams)
            if (t.getPlayerCount() < leastCrowdedTeam.getPlayerCount())
                leastCrowdedTeam = t;
        
        leastCrowdedTeam.addPlayer(p);
        return leastCrowdedTeam;
    }
    
    /**
     * Removes player from team and team chat.
     * 
     * @param player
     *            player to remove
     */
    public void removePlayer(final Player player) {
        if (this.playerInTeam(player)) {
            this.getTeam(player).removePlayer(player);
        }
    }
    
    /**
     * Returns list of managed teams.
     * 
     * @return list of managed teams
     */
    public List<Team> getTeams() {
        return this.teams;
    }
}
