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
import java.util.Arrays;

import com.google.common.base.Charsets;

public class NettyRegisterMesssage {
    private NettyRegisterMesssage() {
        
    }
    
    public static byte[] create(final String authkey, final String name) {
        if (authkey.length() != 128) { throw new IllegalArgumentException("authkey"); }
        
        return ByteBuffer.allocate(4 + 128 + name.length())
                .put((byte) 0)
                .put((byte) 45)
                .put((byte) 89)
                .put((byte) 31)
                .put(authkey.getBytes(Charsets.UTF_8))
                .put(name.getBytes(Charsets.UTF_8))
                .array();
    }
    
    public static boolean validate(final byte[] array, final String authkey) {
        if (array[0] == 0 && array[1] == 45 && array[2] == 89 && array[3] == 31) {
            byte[] received = new byte[128];
            ((ByteBuffer) ByteBuffer.wrap(array).position(4)).get(received, 0, 128);
            return Arrays.equals(authkey.getBytes(Charsets.UTF_8), received);
            
        }
        else {
            return false;
        }
    }
    
    public static String extractName(final byte[] payload) {
        byte[] name = new byte[payload.length - 4 - 128];
        ((ByteBuffer) ByteBuffer.wrap(payload).position(4 + 128)).get(name, 0,
                name.length);
        return new String(name, Charsets.UTF_8);
    }
}
