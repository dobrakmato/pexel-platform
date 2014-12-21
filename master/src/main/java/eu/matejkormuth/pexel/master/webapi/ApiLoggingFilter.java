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

import java.util.logging.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * Logging fitler for API.
 */
public class ApiLoggingFilter implements ContainerRequestFilter {
    private final Logger log = Logger.getLogger(this.getClass().getCanonicalName());
    
    @Override
    public ContainerRequest filter(final ContainerRequest request) {
        String accessKey = request.getQueryParameters().getFirst("accessKey");
        String url = request.getPath();
        this.log.info(accessKey + " accesses " + url);
        return request;
    }
    
    public Logger getLogger() {
        return this.log;
    }
}
