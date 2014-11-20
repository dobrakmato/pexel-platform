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
package eu.matejkormuth.pexel.master.restapi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.master.PexelMaster;

@Path(value = "api")
@Produces({ MediaType.APPLICATION_JSON })
public class ApiResource {
    // Help function.
    @GET
    @Produces({ MediaType.TEXT_HTML })
    @Path("/help")
    public String help() {
        String html = "<html><head></head><body><center><h1>API Documentation</h1></center>";
        // Build help.
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(ApiPart.class)) {
                String localHtml = "<div style=\"border:1px solid black;margin:1em;\">";
                localHtml += "<span style=color:black;background:lime;font-size:18px;display:block;>Path: "
                        + m.getAnnotation(Path.class).value() + "</span>";
                
                for (Annotation a : m.getAnnotations()) {
                    if (a.getClass() == GET.class || a.getClass() == POST.class
                            || a.getClass() == DELETE.class || a.getClass() == PUT.class
                            || a.getClass() == HEAD.class) {
                        localHtml += "<span>Method: " + a.annotationType().getName()
                                + "</span><br/>";
                    }
                }
                
                localHtml += "<span>Description: "
                        + m.getAnnotation(ApiPart.class).desc() + "</span><br/>";
                localHtml += "<span style=\"font-size:14px;\">Parameters: </span><br/>";
                for (Annotation[] a : m.getParameterAnnotations()) {
                    for (Annotation aa : a) {
                        if (aa instanceof PathParam) {
                            localHtml += "- prameter <b>" + ((PathParam) aa).value()
                                    + "</b><br/>";
                        }
                    }
                }
                html += localHtml + "</div>";
            }
        }
        return html + "</body></html>";
    }
    
    @GET
    @Path("/test/{id}")
    public String test(@PathParam("id") final int id) {
        return "{\"test\":55}";
    }
    
    @ApiPart(desc = "Returns array of all servers.")
    @GET
    @Path("/servers")
    public String servers() {
        return "";
    }
    
    @ApiPart(desc = "Returns information about specified server.")
    @GET
    @Path("/server/{id}")
    public String server_view(@PathParam("id") final String serverName) {
        return "{\"name\":\"" + serverName + "\"}";
    }
    
    @ApiPart(desc = "Installs specified plugin on specified slave server.")
    @GET
    @Path("/server/{id}/install/plugin/{pluginName}")
    public String server_plugin_install(@PathParam("id") final String serverName,
            @PathParam("pluginName") final String pluginName) {
        // Check if is server name and plugin name valid,
        if (PexelMaster.getInstance().getStorage().hasPlugin(pluginName)
                && PexelMaster.getInstance().getMasterServer().hasSlave(serverName)) {
            // TODO: Build and send packet to slave server.
            return "{\"error\": \"0\"}";
        }
        else {
            return "{\"error\": \"1\"}";
        }
    }
    
    @ApiPart(desc = "Retrns array of all avaiable maps present in master storage.")
    @GET
    @Path("/maps/avaiable")
    public String maps_avaiable() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiableMaps());
    }
    
    @ApiPart(desc = "Retrns array of all avaiable minigame plugins present in master storage.")
    @GET
    @Path("/plugins/avaiable")
    public String plugins_avaiable() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiablePlugins());
    }
}
