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
import java.nio.charset.Charset;
import java.util.UUID;

import com.google.common.base.Preconditions;

/**
 * Byte utilities.
 */
public class ByteUtils {
    private ByteUtils() {
    }
    
    public static void writeInt(final int i, final byte[] array, final int index) {
        Preconditions.checkArgument(array.length >= index + 4,
                "Array is too small for int at " + index);
        
        array[index + 0] = (byte) (i >> 24);
        array[index + 1] = (byte) (i >> 16);
        array[index + 2] = (byte) (i >> 8);
        array[index + 3] = (byte) (i >> 0);
    }
    
    public static void writeLong(final long i, final byte[] array, final int index) {
        Preconditions.checkArgument(array.length >= index + 8,
                "Array is too small for long at " + index);
        
        array[index + 0] = (byte) (i >> 56);
        array[index + 1] = (byte) (i >> 48);
        array[index + 2] = (byte) (i >> 40);
        array[index + 3] = (byte) (i >> 32);
        array[index + 4] = (byte) (i >> 24);
        array[index + 5] = (byte) (i >> 16);
        array[index + 6] = (byte) (i >> 8);
        array[index + 7] = (byte) (i >> 0);
    }
    
    public static ByteBuffer writeUUID(final ByteBuffer buffer, final UUID uuid) {
        buffer.putLong(uuid.getMostSignificantBits()).putLong(
                uuid.getLeastSignificantBits());
        return buffer;
    }
    
    public static UUID readUUID(final ByteBuffer buffer) {
        return new UUID(buffer.getLong(), buffer.getLong());
    }
    
    public static byte[] merge(final byte[] array1, final byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
    
    public static byte[] toByteArray(final String string) {
        return string.getBytes(Charset.defaultCharset());
    }
    
    public static String toString(final byte[] array) {
        return new String(array, Charset.defaultCharset());
    }
    
    public static long readLong(final byte[] payload, final int i) {
        Preconditions.checkArgument(payload.length >= i + 8,
                "Array is too small for long at " + i);
        long num = 0;
        
        num = num | (payload[i + 0] & 0xff) << 56;
        num = num | (payload[i + 1] & 0xff) << 48;
        num = num | (payload[i + 2] & 0xff) << 40;
        num = num | (payload[i + 3] & 0xff) << 32;
        num = num | (payload[i + 4] & 0xff) << 24;
        num = num | (payload[i + 5] & 0xff) << 16;
        num = num | (payload[i + 6] & 0xff) << 8;
        num = num | (payload[i + 7] & 0xff) << 0;
        
        return num;
    }
    
    public static int readInt(final byte[] payload, final int i) {
        Preconditions.checkArgument(payload.length >= i + 4,
                "Array is too small for int at " + i);
        int num = 0;
        
        num = num | (payload[i + 0] & 0xff) << 24;
        num = num | (payload[i + 1] & 0xff) << 16;
        num = num | (payload[i + 2] & 0xff) << 8;
        num = num | (payload[i + 3] & 0xff) << 0;
        
        return num;
    }
}
