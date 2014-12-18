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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.master.matchmaking.Matchmaking;

@Path(value = "api")
@Produces({ MediaType.APPLICATION_JSON })
public class ApiResource {
    // Help function.
    @GET
    @Produces({ MediaType.TEXT_HTML })
    @Path("/help")
    public String help(@QueryParam("accessKey") final String accessKey) {
        String jquery = "https://code.jquery.com/jquery-2.1.2.min.js";
        String script = "function api_s(text) { $('.api_call').css('display', 'none'); $('.api_call:contains(' + text + ')').css('display', 'block'); }"
                + " function api_ex(url) { $('#api_result_window').hide(); $('#api_result').html(''); $('#api_result').load(url + '?accessKey=' + $('#accessKey').val()); $('#api_result_window').show(400); } ";
        String html = "<html><head><script src="
                + jquery
                + "></script><script>"
                + script
                + "</script></head><body style=\"background:#fafafa;font-family:sans-serif;\">"
                + "<div id=\"api_result_window\" onclick=\"$('#api_result_window').hide(400);\" style=\"display:none;padding:2em;margin:1em;font-family: monospace;white-space: pre;border:1px solid black;box-shadow: 1px 1px 10px 0px rgba(0,0,0,0.23);background: #FFFFE5;border-radius: 3px;\"><b>Result:</b>\n<div id=\"api_result\"></div></div>"
                + "<center><h1>API Documentation (generated)</h1>"
                + "<div><span>Search: </span><input type=\"text\" placeholder=\"start typing...\" onkeyup=\"api_s(this.value);\"> | <span>Access Key:</span><input type=\"text\" id=\"accessKey\" value=\""
                + accessKey + "\"></div>" + "</center>";
        // Build help.
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(ApiPart.class)) {
                StringBuilder localHtml = new StringBuilder(
                        "<div class=\"api_call\" style=\"border:1px solid black;margin:1em;border-radius: 4px;background:#fff;box-shadow: 1px 1px 10px 0px rgba(0,0,0,0.23);\">");
                
                String methodHtml = "";
                String bkgColor = "silver";
                for (Annotation a : m.getAnnotations()) {
                    if (GET.class.isInstance(a) || POST.class.isInstance(a)
                            || DELETE.class.isInstance(a) || PUT.class.isInstance(a)
                            || a.getClass().isAssignableFrom(HEAD.class)) {
                        methodHtml = "<span style=\"background:#fff;padding:4px;margin: 0px 12px 0px 0px;box-shadow:inset 1px 1px 3px rgba(0,0,0,0.5);border-radius:3px;\">"
                                + a.annotationType().getSimpleName() + "</span>";
                        String methodName = a.annotationType().getSimpleName();
                        
                        if (methodName.equalsIgnoreCase("get")) {
                            bkgColor = "rgb(0, 224, 71)";
                        }
                        else if (methodName.equalsIgnoreCase("post")) {
                            bkgColor = "rgb(85, 195, 231)";
                        }
                        else if (methodName.equalsIgnoreCase("delete")) {
                            bkgColor = "rgb(255, 146, 146)";
                        }
                        else if (methodName.equalsIgnoreCase("put")) {
                            bkgColor = "rgb(255, 245, 55)";
                        }
                        
                    }
                }
                
                localHtml.append("<span style=\"color:black;background:"
                        + bkgColor
                        + ";font-size:18px;display:block;padding:8px;border-radius: 4px;\">"
                        + methodHtml
                        + m.getAnnotation(Path.class).value()
                        + "<span style=\"background:white;box-shadow:0px 0px 4px 0px black;border-radius:3px;cursor:pointer;float: right;margin: 0px 11px;padding: 4px;font-size: 14px;\" onclick="
                        + "api_ex('."
                        + m.getAnnotation(Path.class).value()
                        + "')"
                        + ">Execute</span><span style=\"float: right;font-style: italic;font-weight: 100;\">"
                        + m.getAnnotation(ApiPart.class).category() + "</span></span>");
                
                localHtml.append("<div style=\"padding:0.5em;\"><span>Description: "
                        + m.getAnnotation(ApiPart.class).desc() + "</span><br/>");
                
                localHtml.append("<span style=\"font-size:14px;\">Parameters: </span><br/><ul style=\"margin:0px;\">");
                for (Annotation[] a : m.getParameterAnnotations()) {
                    for (Annotation aa : a) {
                        if (aa instanceof PathParam) {
                            localHtml.append("<li>PATH prameter - <b>"
                                    + ((PathParam) aa).value() + "</b></li>");
                        }
                        else if (aa instanceof FormParam) {
                            localHtml.append("<li>POST prameter - <b>"
                                    + ((FormParam) aa).value() + "</b></li>");
                        }
                    }
                }
                html += localHtml + "</ul></div></div>";
            }
        }
        String generatedNotice = "Generated <b>"
                + new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss").format(new Date())
                + "</b> by <b>PexelMaster 1.0</b>.";
        return html + "<center><small>" + generatedNotice
                + "</small></center></body></html>";
    }
    
    //----------------------------------------------------- LOGIN AND AUTH
    
    @ApiPart(desc = "Verifies access key sent in header.", category = "auth")
    @GET
    @Path("/verify")
    public String verify() {
        return "{ \"success\": \"true\" }";
    }
    
    //----------------------------------------------------- USERS
    
    @ApiPart(desc = "Returns informations about specified player by his name.", category = "users")
    @GET
    @Path("/user/{name}")
    public String user_index(@PathParam("name") final String userName) {
        return "{}";
    }
    
    @ApiPart(desc = "Returns all metadata keys and values that belongs to specified player.", category = "users")
    @GET
    @Path("/user/{name}/metadata")
    public String user_metadata(@PathParam("name") final String userName) {
        return "{}";
    }
    
    @ApiPart(desc = "Returns all punishments (bans, kicks, mutes) that belongs to specified player.", category = "users")
    @GET
    @Path("/user/{name}/punishments")
    public String user_punishments(@PathParam("name") final String userName) {
        return "{}";
    }
    
    @ApiPart(desc = "Adds specified punishment to specified player.", category = "users")
    @POST
    @Path("/user/{name}/punishments/add")
    public String user_punishments_add(@PathParam("name") final String userName,
            @FormParam("type") final String type,
            @FormParam("target") final String target,
            @FormParam("length") final int length,
            @FormParam("reason") final String reason) {
        return "{}";
    }
    
    @ApiPart(desc = "Soft deletes specified punishment for specified player.", category = "users")
    @DELETE
    @Path("/user/{name}/punishments/{id}/delete")
    public String user_punishments_softdelete(@PathParam("name") final String userName,
            @PathParam("id") final int id) {
        return "{}";
    }
    
    @ApiPart(desc = "Returns array of UUIDs and names of specified player friends.", category = "users")
    @GET
    @Path("/user/{name}/friends")
    public String user_friends(@PathParam("name") final String userName) {
        return "{}";
    }
    
    //----------------------------------------------------- SLAVES
    
    @ApiPart(desc = "Returns array of all slave servers.", category = "slaves")
    @GET
    @Path("/slaves")
    public String slaves() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getMasterServer()
                .getSlaveServers());
    }
    
    @ApiPart(desc = "Returns information about specified server.", category = "slaves")
    @GET
    @Path("/slave/{name}")
    public String slave_index(@PathParam("name") final String slaveName) {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getMasterServer()
                .getSlave(slaveName));
    }
    
    @ApiPart(desc = "Schedules server stop.", category = "slaves")
    @POST
    @Path("/slave/{name}/stop")
    public String slave_stop(@PathParam("name") final String slaveName) {
        return "{}";
    }
    
    @ApiPart(desc = "Stops server immediatelly and forcefully disconnecting all players from server.", category = "slaves")
    @POST
    @Path("/slave/{name}/forcestop")
    public String slave_forcestop(@PathParam("name") final String slaveName) {
        return "{}";
    }
    
    @ApiPart(desc = "Schedules server restart.", category = "slaves")
    @POST
    @Path("/slave/{name}/restart")
    public String slave_restart(@PathParam("name") final String slaveName) {
        return "{}";
    }
    
    @ApiPart(desc = "Restarts server immediatelly and forcefully disconnecting all players from server.", category = "slaves")
    @POST
    @Path("/slave/{name}/forcerestart")
    public String slave_forcerestart(@PathParam("name") final String slaveName) {
        return "{}";
    }
    
    @ApiPart(desc = "Stops server immediatelly and forcefully disconnecting all players from server.", category = "slaves")
    @POST
    @Path("/slave/{name}/maintenance")
    public String slave_maintenance(@PathParam("name") final String slaveName,
            @FormParam("enabled") final boolean maintenanceEnabled) {
        return "{}";
    }
    
    //--------------------------------------------------- CACHE
    
    @ApiPart(desc = "Returns information about cache on master server.", category = "cache")
    @GET
    @Path("/cache")
    public String cache() {
        return Providers.JSON.toJson(PexelMaster.getInstance().getCaches().toJson());
    }
    
    //---------------------------------------------------- GAMES AND MATCHMAKING
    
    @ApiPart(desc = "Returns array of all games participaing in matchmaking.", category = "matchmaking")
    @GET
    @Path("/games")
    public String games() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getComponent(Matchmaking.class)
                .getGames());
    }
    
    @ApiPart(desc = "Returns information about specified game.", category = "matchmaking")
    @GET
    @Path("/game/{uuid}")
    public String game(@PathParam("uuid") final String uuid) {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getComponent(Matchmaking.class)
                .getGame(UUID.fromString(uuid)));
    }
    
    //--------------------------------------------------- PLUGINS
    
    @ApiPart(desc = "Retrns array of all avaiable minigame plugins present in master storage.", category = "plugins")
    @GET
    @Path("/plugins/avaiable")
    public String plugins_avaiable() {
        // Request remote repo to return list.
        return "{}";
    }
    
    @ApiPart(desc = "Retrns array of all installed minigame plugins present in master storage.", category = "plugins")
    @GET
    @Path("/plugins/installed")
    public String plugins_installed() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiablePlugins());
    }
    
    //---------------------------------------------------- MAPS
    
    @ApiPart(desc = "Retrns array of all avaiable maps present in master storage.", category = "maps")
    @GET
    @Path("/maps/avaiable")
    public String maps_avaiable() {
        return Providers.JSON.toJson(PexelMaster.getInstance()
                .getStorage()
                .getAvaiableMaps());
    }
    
    //---------------------------------------------------- OTHER
    
    @ApiPart(desc = "Installs specified plugin on specified slave server.", category = "other")
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
}
