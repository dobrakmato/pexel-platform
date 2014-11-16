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

import com.google.common.base.Charsets;

import eu.matejkormuth.pexel.network.Request;

public class DebugMessageRequest extends Request {
    public byte   catID;
    public byte   typeID;
    public String content;
    
    public DebugMessageRequest(final byte catID, final byte typeID, final String content) {
        super();
        this.catID = catID;
        this.typeID = typeID;
        this.content = content;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(this.content.length() + 2)
                .put(this.catID)
                .put(this.typeID)
                .put(this.content.getBytes());
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.catID = buffer.get();
        this.typeID = buffer.get();
        byte[] content = new byte[buffer.remaining()];
        buffer.get(content);
        this.content = new String(content, Charsets.UTF_8);
    }
}
