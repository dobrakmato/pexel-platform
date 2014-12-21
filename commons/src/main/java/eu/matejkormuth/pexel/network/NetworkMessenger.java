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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkMessenger implements PayloadHandler {
    
    private final Map<Class<? extends Request>, MethodOfObject> methods = new HashMap<Class<? extends Request>, MethodOfObject>();
    private final CallbackHandler                               callbackHandler;
    private final Protocol                                      protocol;
    private final Logger                                        log     = LoggerFactory.getLogger(NetworkMessenger.class);
    private final Class<Request>                                requestClass;
    
    public NetworkMessenger(final CallbackHandler listener, final Protocol protocol) {
        this.callbackHandler = listener;
        this.protocol = protocol;
        this.requestClass = Request.class;
    }
    
    /**
     * Adds specified valid responder and registers valid handler methods.
     * 
     * @param responder
     *            valid responder object
     */
    @SuppressWarnings("unchecked")
    public void addResponder(final Object responder) {
        this.log.debug("New responder {0}", responder.getClass().getCanonicalName());
        // Register all valid methods.
        for (Method m : responder.getClass().getDeclaredMethods()) {
            Class<?>[] types = m.getParameterTypes();
            // If accepts only one parameter.
            if (types.length == 1) {
                // And that is some request.
                if (this.requestClass.isAssignableFrom(types[0])) {
                    // And that request is supported by protocol.
                    if (this.protocol.supportsRequest((Class<? extends Request>) types[0])) {
                        this.log.debug("  - handles {0}", types[0].getCanonicalName());
                        this.methods.put((Class<? extends Request>) types[0],
                                new MethodOfObject(responder, m));
                    }
                    else {
                        this.log.warn(
                                "Responder {0} has request handler for Request ({1}) that is not supported by current protocol ({2})",
                                responder.getClass().getCanonicalName(),
                                types[0].getCanonicalName(), this.protocol.getClass()
                                        .getCanonicalName());
                    }
                }
            }
        }
    }
    
    private void invokeHandler(final ServerInfo sender, final Request request) {
        try {
            Class<? extends Request> clazz = request.getClass();
            if (!this.methods.containsKey(clazz)) {
                this.log.warn("Request {0} does not have registered responder.",
                        clazz.getCanonicalName());
                return;
            }
            
            Object response = this.methods.get(clazz).invoke(new Object[] { request });
            if (response instanceof Response) {
                sender.sendResponse((Response) response);
            }
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void handleMessage(final ServerInfo sender, final byte[] payload) {
        PayloadDataType type = PayloadDataType.fromType(payload[0]);
        switch (type) {
            case REQUEST:
                this.decodeRequest(sender, payload);
                break;
            case RESPONSE:
                this.decodeResponse(sender, payload);
                break;
            case OTHER:
                
                break;
        }
    }
    
    private void decodeResponse(final ServerInfo sender, final byte[] payload) {
        long requestID = ByteUtils.readLong(payload, 1);
        int responseType = ByteUtils.readInt(payload, 9);
        
        try {
            // Create request object.
            Response response = this.protocol.getResponse(responseType).newInstance();
            response.requestID = requestID;
            
            byte[] packetData = new byte[payload.length - 13];
            System.arraycopy(payload, 13, packetData, 0, packetData.length);
            response.fromByteBuffer(ByteBuffer.wrap(packetData));
            
            // Redirect to callback handler.
            this.callbackHandler.onResponse(response);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void decodeRequest(final ServerInfo sender, final byte[] payload) {
        long requestID = ByteUtils.readLong(payload, 1);
        int requestType = ByteUtils.readInt(payload, 9);
        
        try {
            // Create request object.
            Class<? extends Request> requestTypeClazz = this.protocol.getRequest(requestType);
            Request request = requestTypeClazz.newInstance();
            request.requestID = requestID;
            request.sender = sender;
            byte[] packetData = new byte[payload.length - 13];
            System.arraycopy(payload, 13, packetData, 0, packetData.length);
            request.fromByteBuffer(ByteBuffer.wrap(packetData));
            
            // Find and invoke handler.
            this.invokeHandler(sender, request);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
}
