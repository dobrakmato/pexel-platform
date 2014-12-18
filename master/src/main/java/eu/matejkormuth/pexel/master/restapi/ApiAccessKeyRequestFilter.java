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

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import eu.matejkormuth.pexel.master.PexelMaster;

/**
 * Filter for Access-Key header.
 */
@Provider
public class ApiAccessKeyRequestFilter implements ContainerRequestFilter {
    private ApiAccessProvider provider = null;
    
    @Override
    public ContainerRequest filter(final ContainerRequest request) {
        String accessKey = request.getHeaderValue("Access-Key");
        if (accessKey == null) {
            ResponseBuilder builder = null;
            builder = Response.status(Response.Status.UNAUTHORIZED).entity(
                    new ApiErrorJson(ApiErrorJson.NO_ACCESSKEY_HEADER,
                            "No Access-Key header!").getJson());
            throw new WebApplicationException(builder.build());
        }
        
        if (this.provider == null) {
            this.provider = PexelMaster.getInstance().getComponent(
                    ApiAccessProvider.class);
        }
        
        if (!this.provider.isValid(accessKey)) {
            ResponseBuilder builder = null;
            builder = Response.status(Response.Status.UNAUTHORIZED).entity(
                    new ApiErrorJson(ApiErrorJson.INVALID_ACCESSKEY,
                            "Invalid Access-Key!").getJson());
            throw new WebApplicationException(builder.build());
        }
        return request;
    }
}
