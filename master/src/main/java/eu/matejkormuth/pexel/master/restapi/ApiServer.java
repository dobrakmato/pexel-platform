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

import java.io.Closeable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.master.MasterComponent;

/**
 * Class that respresents REST API server.
 */
public class ApiServer extends MasterComponent {
    private Closeable http_server;
    private Closeable https_server;
    
    @Override
    public void onEnable() {
        try {
            String http_address = "http://0.0.0.0:"
                    + this.getMaster()
                            .getConfiguration()
                            .getAsInt(Configuration.KEY_PORT_API_HTTP);
            String https_address = "https://0.0.0.0:"
                    + this.getMaster()
                            .getConfiguration()
                            .getAsInt(Configuration.KEY_PORT_API_HTTPS);
            
            DefaultResourceConfig resourceConfig = new DefaultResourceConfig(
                    ApiResource.class, StringBodyWriter.class);
            this.logger.info("Starting HTTP api server...");
            this.http_server = SimpleServerFactory.create(http_address, resourceConfig);
            
            //SelfSignedCertificate ssc = new SelfSignedCertificate();
            this.logger.info("Starting HTTPS api server...");
            SSLContext context = SSLContext.getDefault();
            
            this.https_server = SimpleServerFactory.create(https_address, context,
                    resourceConfig);
        } catch (IllegalArgumentException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onDisable() {
        try {
            this.logger.info("Stopping HTTP and HTTPS api servers...");
            this.http_server.close();
            this.https_server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
