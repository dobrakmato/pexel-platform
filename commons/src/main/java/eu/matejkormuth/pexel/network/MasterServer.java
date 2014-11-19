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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.ConfigurationSection;
import eu.matejkormuth.pexel.commons.Logger;

/**
 * Main class of master server.
 */
public class MasterServer extends ServerInfo implements Requestable {
    private final Platform                 platform;
    private final Proxy                    proxy;
    private final Logger                   log;
    private final Protocol                 protocol;
    private final Messenger                messenger;
    private final CallbackHandler          callbackHandler;                                   // May be replaced with array of listeners.
    private final MessageComunicator       comunicator;
    private final ConfigurationSection     config;
    
    private final AtomicLong               lastRequestID = new AtomicLong();
    private final Map<Long, Callback<?>>   callbacks     = new HashMap<Long, Callback<?>>(
                                                                 255);
    private final Map<String, SlaveServer> slaves        = new HashMap<String, SlaveServer>();
    
    public MasterServer(final String name, final ConfigurationSection config,
            final Logger logger, final Protocol protocol) {
        super(name);
        
        // Logger.
        this.log = logger.getChild("Net");
        
        this.log.info("Starting MasterServer...");
        
        this.config = config;
        
        // Currently we support only proxy and only bungee.
        this.platform = Platform.MINECRAFT_PROXY;
        this.proxy = new BungeeProxy();
        
        // Protocol - PexelProtocol has registered all packets.
        this.protocol = protocol;
        
        // Callback handler.
        this.callbackHandler = new CallbackHandler(this);
        
        // Create message decoder.
        this.messenger = new Messenger(this.callbackHandler, this.protocol);
        
        // Start netty comunicator.
        this.comunicator = new NettyServerComunicator(this.messenger, this.config.get(
                Configuration.Keys.KEY_PORT, 29631).asInteger(), this.config.get(
                Configuration.Keys.KEY_AUTHKEY, Configuration.Defaults.AUTH_KEY)
                .asString(), this);
        this.comunicator.start();
        
        // Update instance in ServerInfo
        ServerInfo.setLocalServer(this);
        this.side = ServerSide.LOCAL;
        this.log.info("Network part loaded successfully!");
    }
    
    // Adds a slave server.
    protected void addSlave(final SlaveServer server) {
        this.slaves.put(server.getName(), server);
    }
    
    /**
     * Shutsdown all workers and closes all connections.
     */
    public void shutdown() {
        this.log.info("Shutting down...");
        this.comunicator.stop();
        this.log.info("Saving config...");
    }
    
    /**
     * Returns configuration.
     * 
     * @return configuration object of master server
     */
    public ConfigurationSection getConfiguration() {
        return this.config;
    }
    
    /**
     * Returns {@link Logger}.
     * 
     * @return the logger
     */
    protected Logger getLogger() {
        return this.log;
    }
    
    /**
     * Sends Message to specified server.
     * 
     * @param message
     *            message to be send
     * @param target
     *            target server
     */
    public void send(final Message message, final ServerInfo target) {
        this.comunicator.send(target, this.protocol.getPayload(message));
    }
    
    /**
     * Returns collection of all connected slave servers.
     * 
     * @return collection of connected slave servers
     */
    public Collection<SlaveServer> getSlaveServers() {
        return this.slaves.values();
    }
    
    /**
     * Returns messanger object.
     * 
     * @return current messanger
     */
    public Messenger getMessenger() {
        return this.messenger;
    }
    
    /**
     * Returns platform the master is running on.
     * 
     * @return platform of master server
     */
    public Platform getPlatform() {
        return this.platform;
    }
    
    /**
     * Returns currently used protocol.
     * 
     * @return currently used protocol
     */
    public Protocol getProtocol() {
        return this.protocol;
    }
    
    /**
     * Returns {@link Proxy} used by master server.
     * 
     * @return proxy object
     */
    public Proxy getProxy() {
        return this.proxy;
    }
    
    /**
     * Returns {@link ServerInfo} by name or null if not found.
     * 
     * @param name
     *            name of the server
     * @return slave server or null
     */
    public ServerInfo getServer(final String name) {
        return this.slaves.get(name);
    }
    
    @Override
    public long nextRequestID() {
        return this.lastRequestID.getAndIncrement();
    }
    
    @Override
    public void registerCallback(final long requestID, final Callback<?> callback) {
        this.callbacks.put(requestID, callback);
    }
    
    @Override
    public Callback<?> getCallback(final long requestID) {
        return this.callbacks.get(requestID);
    }
    
    @Override
    public void removeCallback(final long requestID) {
        this.callbacks.get(requestID);
    }
    
    public boolean hasSlave(final String serverName) {
        return this.slaves.containsKey(serverName);
    }
}
