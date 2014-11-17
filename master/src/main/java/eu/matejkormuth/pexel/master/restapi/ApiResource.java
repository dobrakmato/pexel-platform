package eu.matejkormuth.pexel.master.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.master.PexelMaster;

@Path(value = "api")
@Produces({ MediaType.APPLICATION_JSON })
public class ApiResource {
    @GET
    @Path("/test/{id}")
    public String test(@PathParam("id") final int id) {
        return "{\"test\":55}";
    }
    
    @GET
    @Path("/servers")
    public String servers() {
        return "[]";
    }
    
    @GET
    @Path("/maps/avaiable")
    public String maps_avaiable() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiableMaps());
    }
    
    @GET
    @Path("/plugins/avaiable")
    public String plugins_avaiable() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiablePlugins());
    }
}
