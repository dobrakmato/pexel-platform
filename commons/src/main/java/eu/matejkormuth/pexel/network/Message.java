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

import java.nio.ByteBuffer;

/**
 * Class that represents message over network.
 */
public abstract class Message {
    /**
     * ID of request that involved creation of this message.
     */
    protected long requestID;
    
    /**
     * Returns byte array representation of this message.
     * 
     * @return
     */
    public abstract ByteBuffer toByteBuffer();
    
    /**
     * Should constrct Message ({@link Request}, {@link Response}, ...) from byte array.
     * 
     * @param buffer
     *            array containing data
     */
    public abstract void fromByteBuffer(ByteBuffer buffer);
}
