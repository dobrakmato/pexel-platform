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
package eu.matejkormuth.pexel.network;

import java.util.UUID;

import com.google.common.base.Optional;

/**
 * Interface that represents proxy.
 */
public interface Proxy {
    /**
     * Returns brand of this proxy server.
     * 
     * @return brand of this server
     */
    public ProxyBrand getBrand();
    
    /**
     * Returns {@link ProxiedPlayer} instance by specified UUID, null if not found.
     * 
     * @param uuid
     *            UUID of player
     * @return ProxiedPlayer or null, if not found.
     */
    public ProxiedPlayer getPlayer(UUID uuid);
    
    /**
     * Transfers specified player to spcified server.
     * 
     * @param player
     *            player to transfer
     * @param target
     *            target server
     */
    public void connect(ProxiedPlayer player, ServerInfo target);
    
    /**
     * Sends chat message to specified player.
     * 
     * @param player
     *            player
     * @param message
     *            message to be send
     */
    public void sendMessage(ProxiedPlayer player, String message);
    
    /**
     * Broadcasts specified message to all conected servers.
     * 
     * @param message
     *            message to broadcast
     */
    public void broadcast(String message);
    
    /**
     * Returns {@link ServerInfo} of specified server name or absent value if server not found.
     * 
     * @param name
     *            name of server
     */
    public Optional<ServerInfo> getServer(String name);
}
