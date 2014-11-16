package eu.matejkormuth.pexel.master.restapi;

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

@Produces("application/json")
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