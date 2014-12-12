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
package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;

import eu.matejkormuth.pexel.commons.TransferPurpose;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Request that carries file.
 */
public class FileTransferRequest extends Request {
    public TransferPurpose tranfserPurpose;
    public String          name;
    public byte[]          data;
    
    public FileTransferRequest() {
    }
    
    public FileTransferRequest(final TransferPurpose tranfserPurpose, final String name,
            final byte[] data) {
        this.tranfserPurpose = tranfserPurpose;
        this.name = name;
        this.data = data;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(1 + this.name.length() + this.data.length)
                .put(this.tranfserPurpose.getByte())
                .put(this.name.getBytes(PexelProtocol.CHARSET))
                .put(this.data);
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        // TODO Auto-generated method stub
        
    }
}
