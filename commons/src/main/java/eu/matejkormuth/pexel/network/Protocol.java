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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Class that handles protocol mapping requests and responses.
 */
public abstract class Protocol {
    // Mapping request types.
    private final BiMap<Class<? extends Request>, Integer>  requestmapping  = HashBiMap.create();
    // Mapping response types.
    private final BiMap<Class<? extends Response>, Integer> responsemapping = HashBiMap.create();
    
    /**
     * Registers new Request type to specified ID.
     * 
     * @param id
     *            id that should be used in network transfer
     * @param request
     *            request type
     */
    public void registerRequest(final int id, final Class<? extends Request> request) {
        if (this.requestmapping.containsValue(id)) {
            throw new RuntimeException("ID " + id + " is already occupied!");
        }
        else {
            this.requestmapping.put(request, id);
        }
    }
    
    /**
     * Registers new Response type to specified ID.
     * 
     * @param id
     *            id that should be used in network transfer
     * @param response
     *            response type
     */
    public void registerResponse(final int id, final Class<? extends Response> response) {
        if (this.responsemapping.containsValue(id)) {
            throw new RuntimeException("ID " + id + " is already occupied!");
        }
        else {
            this.responsemapping.put(response, id);
        }
    }
    
    /**
     * Returns valid packet payload containing header and Message data.
     * 
     * @param message
     *            message that data should be added to byte array
     * @return byte array of payload
     */
    public byte[] getPayload(final Message message) {
        byte[] data = message.toByteBuffer().array();
        byte[] newarray = new byte[data.length + 13];
        System.arraycopy(data, 0, newarray, 13, data.length);
        
        if (message instanceof Request) {
            newarray[0] = PayloadDataType.REQUEST.getType();
            ByteUtils.writeLong(message.requestID, newarray, 1);
            ByteUtils.writeInt(this.requestmapping.get(message.getClass()), newarray, 9);
            return newarray;
        }
        else if (message instanceof Response) {
            newarray[0] = PayloadDataType.RESPONSE.getType();
            ByteUtils.writeLong(message.requestID, newarray, 1);
            ByteUtils.writeInt(this.responsemapping.get(message.getClass()), newarray, 9);
            return newarray;
        }
        else {
            newarray[0] = PayloadDataType.OTHER.getType();
            ByteUtils.writeLong(message.requestID, newarray, 1);
            ByteUtils.writeInt(-1, newarray, 9);
            return newarray;
        }
    }
    
    /**
     * Returns whether specified type is supported by this protocol.
     * 
     * @param type
     *            of request.
     * @return true or false
     */
    public boolean supportsRequest(final Class<? extends Request> type) {
        return this.requestmapping.containsKey(type);
    }
    
    /**
     * Returns request type by specified type or null if the type is not registered.
     * 
     * @param requestType
     *            network id - type of request
     */
    public Class<? extends Request> getRequest(final int requestType) {
        return this.requestmapping.inverse().get(requestType);
    }
    
    /**
     * Returns response type by specified type or null if the type is not registered.
     * 
     * @param responseType
     *            network id - type of response
     */
    public Class<? extends Response> getResponse(final int responseType) {
        return this.responsemapping.inverse().get(responseType);
    }
    
    /**
     * @param request
     * @return
     */
    public int getRequestType(final Request request) {
        return this.requestmapping.get(request.getClass());
    }
    
    /**
     * @param response
     * @return
     */
    public int getResponseType(final Response response) {
        return this.responsemapping.get(response.getClass());
    }
}
