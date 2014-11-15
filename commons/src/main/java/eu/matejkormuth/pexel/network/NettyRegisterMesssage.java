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

public class NettyRegisterMesssage {
    private NettyRegisterMesssage() {
        
    }
    
    public static byte[] create(final String authkey, final String name) {
        if (authkey.length() <= 128) { throw new RuntimeException(
                "Specified key is not valid authKey!"); }
        return ByteBuffer.allocate(4 + 128 + name.length())
                .put((byte) 0)
                .put((byte) 45)
                .put((byte) 89)
                .put((byte) 31)
                .put(authkey.getBytes())
                .put(name.getBytes())
                .array();
    }
    
    public static boolean validate(final byte[] array, final String authkey) {
        if (array.length <= 128) {
            if (array[0] == 0 && array[1] == 45 && array[2] == 89 && array[3] == 31) {
                return authkey.equals(new String(ByteBuffer.wrap(array, 4, 128).array()));
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    public static String getName(final byte[] payload) {
        return new String(ByteBuffer.wrap(payload, 132, payload.length - 132).array());
    }
}
