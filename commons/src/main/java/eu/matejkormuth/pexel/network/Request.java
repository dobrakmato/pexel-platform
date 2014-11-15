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

/**
 * Class that specifies request.
 */
public abstract class Request extends Message {
    protected long       requestID;
    // Null when sending, hold sender object when processing.
    protected ServerInfo sender;
    
    public Request() {
        this.requestID = 0;
    }
    
    /**
     * Sends this request to specified server.
     * 
     * @param target
     *            target server.
     */
    public void send(final ServerInfo target) {
        target.sendRequest(this);
    }
}
