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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class that represents method of object.
 */
public class MethodOfObject {
    protected Method method;
    protected Object object;
    
    public MethodOfObject(final Object object, final Method method) {
        this.object = object;
        this.method = method;
    }
    
    public Object invoke(final Object... args) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        return this.method.invoke(this.object, args);
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public Object getObject() {
        return this.object;
    }
}
