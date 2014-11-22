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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents server on network.
 */
public class ServerInfo {
    private static ServerInfo     local;
    
    protected final String        name;
    protected ServerSide          side;
    protected Map<String, Object> custom;
    
    public ServerInfo(final String name) {
        this.name = name;
    }
    
    public void sendResponse(final Response response) {
        throw new OperationNotSupportedException(
                "ServerInfo does not support sending responses!");
    }
    
    public void sendRequest(final Request request) {
        throw new OperationNotSupportedException(
                "ServerInfo does not support sending requests!");
    }
    
    public boolean isLocal() {
        return this.side == ServerSide.LOCAL;
    }
    
    public ServerSide getSide() {
        return this.side;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static ServerInfo localServer() {
        return ServerInfo.local;
    }
    
    public void setCustom(final String key, final String value) {
        if (this.custom == null) {
            this.custom = new HashMap<String, Object>();
        }
        this.custom.put(key, value);
    }
    
    public void setCustom(final String key, final Object value) {
        if (this.custom == null) {
            this.custom = new HashMap<String, Object>();
        }
        this.custom.put(key, value);
    }
    
    public String getCustomAsString(final String key) {
        if (this.custom == null) {
            return null;
        }
        else {
            return (String) this.custom.get(key);
        }
    }
    
    public Object getCustomAsObject(final String key) {
        if (this.custom == null) {
            return null;
        }
        else {
            return this.custom.get(key);
        }
    }
    
    public Map<String, Object> getCustom() {
        return Collections.unmodifiableMap(this.custom);
    }
    
    protected static void setLocalServer(final ServerInfo server) {
        if (ServerInfo.local == null) {
            ServerInfo.local = server;
        }
        else {
            throw new RuntimeException("Field local has been initialized before!");
        }
    }
}
