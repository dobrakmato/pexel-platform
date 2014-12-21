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
package eu.matejkormuth.pexel.master.webapi;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.common.base.Charsets;

@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_HTML })
public class StringBodyWriter implements MessageBodyWriter<String> {
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType) {
        return type == String.class;
    }
    
    @Override
    public long getSize(final String data, final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return data.length();
    }
    
    @Override
    public void writeTo(final String data, final Class<?> type, final Type genericType,
            final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, Object> httpHeaders,
            final OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(data.getBytes(Charsets.UTF_8));
    }
}