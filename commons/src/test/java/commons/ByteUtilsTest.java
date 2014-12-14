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
package commons;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import eu.matejkormuth.pexel.network.ByteUtils;

public class ByteUtilsTest {
    private final Random random = new Random();
    
    /**
     * Test method for {@link eu.matejkormuth.pexel.network.ByteUtils#writeInt(int, byte[], int)}.
     */
    @Test
    public void testInt() {
        int i = this.random.nextInt();
        byte[] data = new byte[4];
        ByteUtils.writeInt(i, data, 0);
        int k = ByteUtils.readInt(data, 0);
        assertEquals(i, k);
    }
    
    /**
     * Test method for {@link eu.matejkormuth.pexel.network.ByteUtils#writeLong(long, byte[], int)}.
     */
    @Test
    public void testLong() {
        long i = this.random.nextLong();
        byte[] data = new byte[8];
        ByteUtils.writeLong(i, data, 0);
        long k = ByteUtils.readLong(data, 0);
        assertEquals(i, k);
    }
    
    /**
     * Test method for {@link eu.matejkormuth.pexel.network.ByteUtils#readUUID(java.nio.ByteBuffer)}.
     */
    @Test
    public void testUUID() {
        UUID uuid = UUID.randomUUID();
        byte[] data = new byte[16];
        ByteUtils.writeUUID(ByteBuffer.wrap(data), uuid);
        UUID uuid2 = ByteUtils.readUUID(ByteBuffer.wrap(data));
        assertEquals(uuid, uuid2);
    }
    
}
