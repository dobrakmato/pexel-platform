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
package eu.matejkormuth.pexel.protocol;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;

import eu.matejkormuth.pexel.network.Protocol;
import eu.matejkormuth.pexel.protocol.requests.DebugMessageRequest;
import eu.matejkormuth.pexel.protocol.requests.FileTransferRequest;
import eu.matejkormuth.pexel.protocol.requests.ServerConfigurationRequest;
import eu.matejkormuth.pexel.protocol.requests.ServerStatusRequest;
import eu.matejkormuth.pexel.protocol.requests.SlaveServerSetModeRequest;
import eu.matejkormuth.pexel.protocol.responses.ServerStatusResponse;

/**
 * Protocol that supports all pexel request and responses.
 */
public class PexelProtocol extends Protocol {
    // Protocol charset.
    public static final Charset CHARSET = Charsets.UTF_8;
    
    public PexelProtocol() {
        this.registerRequest(1, ServerStatusRequest.class);
        this.registerRequest(2, DebugMessageRequest.class);
        this.registerRequest(3, FileTransferRequest.class);
        this.registerRequest(4, ServerConfigurationRequest.class);
        this.registerRequest(5, SlaveServerSetModeRequest.class);
        
        this.registerResponse(1, ServerStatusResponse.class);
    }
}
