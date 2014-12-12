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
package protocol;

import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;

import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.network.Response;

public class NoArgConstructorTest {
    
    @Test
    public void test() {
        boolean failed = false;
        Reflections reflections_requests = new Reflections(
                "eu.matejkormuth.pexel.protocol.requests");
        Set<Class<? extends Request>> requests = reflections_requests.getSubTypesOf(Request.class);
        
        Reflections reflections_responses = new Reflections(
                "eu.matejkormuth.pexel.protocol.responses");
        Set<Class<? extends Response>> responses = reflections_responses.getSubTypesOf(Response.class);
        
        System.out.println(requests.size() + " requests and " + responses.size()
                + " responses");
        
        for (Class<? extends Request> clazz : requests) {
            try {
                clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println(clazz + " does not have no argument constructor!");
                failed |= true;
            }
        }
        
        for (Class<? extends Response> clazz : responses) {
            try {
                clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println(clazz + " does not have no argument constructor!");
                failed |= true;
            }
        }
        
        if (failed) {
            fail("at least one class found that does NOT have no arg constructor");
        }
    }
    
}
