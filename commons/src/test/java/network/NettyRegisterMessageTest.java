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
package network;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.matejkormuth.pexel.network.NettyRegisterMesssage;

public class NettyRegisterMessageTest {
    String name    = "Alice";
    String authKey = "3IxPOzV0eURnYVP718z5qFhntc4HZr8S0wyEVjv04ddSZSQQQgM6MTk5WfDLwCumYSRJ2dzXhDG6lmN2tpZ59a1VfuxCXSPLAxktBKgIdNFpZihiy7ey75jdqHGdplOP";
    
    @Test(expected = IllegalArgumentException.class)
    public void test_create_short_authkey() {
        NettyRegisterMesssage.create("short authkey", this.name);
    }
    
    @Test
    public void test_create() {
        byte[] data = NettyRegisterMesssage.create(this.authKey, this.name);
        assertTrue("lenght", data.length == 4 + 128 + this.name.length());
    }
    
    @Test
    public void test_validate() {
        byte[] data = NettyRegisterMesssage.create(this.authKey, this.name);
        assertTrue("authkey validation",
                NettyRegisterMesssage.validate(data, this.authKey));
    }
    
    @Test
    public void test_getname() {
        byte[] data = NettyRegisterMesssage.create(this.authKey, this.name);
        assertTrue("name equality after extraction",
                NettyRegisterMesssage.extractName(data).equals(this.name));
    }
}
