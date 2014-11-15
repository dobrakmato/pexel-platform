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
 * Class that supports receiving and sending requests / reponses.
 */
public interface Requestable {
    /**
     * Returns next unique request id for this requestable.
     * 
     * @return unique request id
     */
    public long nextRequestID();
    
    /**
     * Registers specifies callback with specified request id. Callback will be called, when response to request
     * arrives.
     * 
     * @param requestID
     *            id of request
     * @param callback
     *            callback
     */
    public void registerCallback(long requestID, Callback<?> callback);
    
    /**
     * Returns callback by request id.
     * 
     * @param requestID
     *            request id
     */
    public Callback<?> getCallback(long requestID);
    
    /**
     * Removes callback for specified request id.
     * 
     * @param requestID
     *            request id
     */
    public void removeCallback(long requestID);
}
