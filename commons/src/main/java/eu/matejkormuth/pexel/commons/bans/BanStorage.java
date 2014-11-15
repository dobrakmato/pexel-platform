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
package eu.matejkormuth.pexel.commons.bans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

/**
 * Class used as ban storage.
 */
public class BanStorage {
    private final HashMap<UUID, List<PlayerBan>> activeBans  = new HashMap<UUID, List<PlayerBan>>();
    private final HashMap<UUID, List<PlayerBan>> historyBans = new HashMap<UUID, List<PlayerBan>>();
    
    public void addBan(final PlayerBan ban) {
        if (this.activeBans.containsKey(ban.uuid)) {
            this.activeBans.get(ban.uuid).add(ban);
        }
        else {
            this.activeBans.put(ban.uuid, new ArrayList<PlayerBan>());
            this.activeBans.get(ban.uuid).add(ban);
        }
    }
    
    /**
     * Return if is player banned on specified part.
     * 
     * @param player
     *            player
     * @param part
     *            bannable part
     * @return true or false
     */
    public boolean isBanned(final Player player, final Bannable part) {
        if (!this.activeBans.containsKey(player.getUniqueId())) {
            return false;
        }
        else {
            for (Iterator<PlayerBan> iterator = this.activeBans.get(player.getUniqueId()).iterator(); iterator.hasNext();) {
                PlayerBan ban = iterator.next();
                if (ban.getPart() == part)
                    if (ban.isPermanent()) {
                        return true;
                    }
                    else {
                        if (ban.getExpirationTime() < System.currentTimeMillis()) {
                            if (this.historyBans.containsKey(player.getUniqueId())) {
                                this.historyBans.get(player.getUniqueId()).add(ban);
                            }
                            else {
                                this.historyBans.put(player.getUniqueId(),
                                        new ArrayList<PlayerBan>());
                                this.historyBans.get(player.getUniqueId()).add(ban);
                            }
                            iterator.remove();
                            return false;
                        }
                        else {
                            return true;
                        }
                    }
            }
            return false;
        }
    }
    
    /**
     * Returns ban or null.
     * 
     * @param player
     *            specified player
     * @param part
     *            specified part
     * @return ban or null
     */
    public PlayerBan getBan(final Player player, final Bannable part) {
        if (!this.activeBans.containsKey(player.getUniqueId())) {
            return null;
        }
        else {
            for (PlayerBan ban : this.activeBans.get(player.getUniqueId())) {
                if (ban.getPart() == part)
                    return ban;
            }
            return null;
        }
    }
    
    /**
     * Retruns list of active bans by specified player.
     * 
     * @param player
     *            specififed player.
     * @return list of player's bans
     */
    public List<PlayerBan> getActiveBans(final Player player) {
        return this.activeBans.get(player.getUniqueId());
    }
    
    /**
     * Retruns list of past bans by specified player.
     * 
     * @param player
     *            specififed player.
     * @return list of player's bans
     */
    public List<PlayerBan> getPastBans(final Player player) {
        return this.historyBans.get(player.getUniqueId());
    }
    
    public void save() {
        
    }
    
    public void load() {
        
    }
    
    public List<PlayerBan> getBans() {
        List<PlayerBan> fullbans = new ArrayList<PlayerBan>();
        for (List<PlayerBan> bans : this.activeBans.values())
            fullbans.addAll(bans);
        return fullbans;
    }
}
