package eu.matejkormuth.pexel.master.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/api")
public class ApiResource {
    @GET
    @Path("/test/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public String test(@PathParam("id") final int id) {
        return "{\"test\":55}";
    }
}
