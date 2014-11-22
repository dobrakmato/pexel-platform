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

import eu.matejkormuth.pexel.commons.Asynchronous;
import eu.matejkormuth.pexel.commons.Commitable;

/**
 * Class that maintains sending and receveing packets on updated objects.
 */
public abstract class MessageExtender<Rq extends Request, Rs extends Response> implements
        Commitable {
    private ServerInfo         destination;
    private ServerType         side;
    private final Callback<Rs> callback;
    
    public MessageExtender(final ServerType side) {
        if (side == ServerType.MASTER) {
            this.destination = null;
        }
        else {
            this.destination = ((SlaveServer) ServerInfo.localServer()).getMasterServerInfo();
        }
        
        this.callback = new Callback<Rs>() {
            @Override
            public void onResponse(final Rs response) {
                MessageExtender.this.onResponse(response);
            }
        };
    }
    
    @Override
    public void commit() {
        if (this.side != ServerType.MASTER) { throw new RuntimeException(
                "You must specify target when using send() method."); }
        
        Rq request = this.onRequest();
        this.destination.sendRequest(request);
    }
    
    public void send(final ServerInfo target) {
        if (this.side != ServerType.SLAVE) { throw new RuntimeException(
                "You must specify target when using send() method."); }
        
        Rq request = this.onRequest();
        target.sendRequest(request);
    }
    
    public ServerInfo getDestination() {
        return this.destination;
    }
    
    public ServerType getSide() {
        return this.side;
    }
    
    public Callback<Rs> getCallback() {
        return this.callback;
    }
    
    @Asynchronous
    public abstract void onResponse(final Rs response);
    
    @Asynchronous
    public abstract Rq onRequest();
}
