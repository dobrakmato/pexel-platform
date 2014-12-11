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

public class Messenger implements PayloadHandler {
    private final Map<Class<? extends Request>, MethodOfObject> methods = new HashMap<Class<? extends Request>, MethodOfObject>();
    private final CallbackHandler                       callbackHandler;
    private final Protocol                              protocol;
    
    public Messenger(final CallbackHandler listener, final Protocol protocol) {
        this.callbackHandler = listener;
        this.protocol = protocol;
    }
    
    /**
     * Adds specified valid responder and registers valid handler methods.
     * 
     * @param responder
     *            valid responder object
     */
    @SuppressWarnings("unchecked")
    public void addResponder(final Object responder) {
        // Register all valid methods.
        for (Method m : responder.getClass().getDeclaredMethods()) {
            Class<?>[] types = m.getParameterTypes();
            // If accepts only one parameter.
            if (types.length == 1) {
                // And that is some request.
                if (types[0].isAssignableFrom(Request.class)) {
                    // And that request is supported by protocol.
                    if (this.protocol.supportsRequest((Class<? extends Request>) types[0])) {
                        this.methods.put((Class<? extends Request>) types[0],
                                new MethodOfObject(responder, m));
                    }
                }
            }
        }
    }
    
    private void invokeHandler(final ServerInfo sender, final Request request) {
        try {
            Object response = this.methods.get(request.getClass()).invoke(
                    new Object[] { request });
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
            response.fromByteBuffer(ByteBuffer.wrap(payload, 13, payload.length - 13));
            
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
            Request request = this.protocol.getRequest(requestType).newInstance();
            request.requestID = requestID;
            request.fromByteBuffer(ByteBuffer.wrap(payload, 13, payload.length - 13));
            
            // Find and invoke handler.
            this.invokeHandler(sender, request);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
}
