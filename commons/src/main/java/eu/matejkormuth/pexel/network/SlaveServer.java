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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.commons.ConfigurationSection;
import eu.matejkormuth.pexel.commons.Logger;

public class SlaveServer extends ServerInfo implements Requestable {
    
    // Requestable interface
    protected AtomicLong             lastRequestID = new AtomicLong();
    protected Map<Long, Callback<?>> callbacks     = new HashMap<Long, Callback<?>>(255);
    
    protected Messenger              messenger;
    protected MessageComunicator     comunicator;
    protected ServerInfo             masterServerInfo;
    protected Protocol               protocol;
    protected ConfigurationSection   config;
    protected Logger                 log;
    
    public SlaveServer(final String name, final ConfigurationSection config,
            final Logger logger, final Protocol protocol) {
        super(name);
        
        this.log = logger.getChild("Net");
        
        this.config = config;
        
        this.side = ServerSide.LOCAL;
        this.log.info("Initializing protocol...");
        this.protocol = protocol;
        this.log.info("Initializing Messenger...");
        this.messenger = new Messenger(new CallbackHandler(this), this.protocol);
        
        this.masterServerInfo = new ServerInfo("master") {
            @Override
            public void sendRequest(final Request request) {
                SlaveServer.this.comunicator.send(SlaveServer.this.masterServerInfo,
                        request.toByteBuffer().array());
            }
            
            @Override
            public void sendResponse(final Response response) {
                SlaveServer.this.comunicator.send(SlaveServer.this.masterServerInfo,
                        response.toByteBuffer().array());
            }
        };
        this.log.info("Initializing NettyClientComunicator...");
        this.comunicator = new NettyClientComunicator(this.messenger, this.config.get(
                Configuration.Keys.KEY_PORT, 29631).asInteger(), this.config.get(
                Configuration.Keys.KEY_MASTER_IP, "127.0.0.1").asString(),
                this.config.get(Configuration.Keys.KEY_AUTHKEY,
                        Configuration.Defaults.AUTH_KEY).asString(), this);
        
        ServerInfo.setLocalServer(this);
        this.log.info("Network part loaded successfully!");
    }
    
    /**
     * Hidden constructor. Used only from {@link NettyServerComunicator}.
     * 
     * @param name
     */
    protected SlaveServer(final String name) {
        super(name);
        
        // Does not register this as local server.
        this.side = ServerSide.REMOTE;
    }
    
    @Override
    public void sendRequest(final Request request) {
        if (this.side == ServerSide.REMOTE) {
            // Sending from master
            ((MasterServer) ServerInfo.localServer()).send(request, this);
        }
        else {
            throw new RuntimeException("Can't send request to local server.");
        }
    }
    
    @Override
    public void sendResponse(final Response response) {
        if (this.side == ServerSide.REMOTE) {
            // Sending from master
            ((MasterServer) ServerInfo.localServer()).send(response, this);
        }
        else {
            throw new RuntimeException("Can't send response to local server.");
        }
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
        this.callbacks.remove(requestID);
    }
    
    public ServerInfo getMasterServerInfo() {
        return this.masterServerInfo;
    }
    
    public Logger getLogger() {
        return this.log;
    }
}
