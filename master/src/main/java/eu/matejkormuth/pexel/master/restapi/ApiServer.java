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

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.master.Component;

/**
 * Class that respresents REST API server.
 */
public class ApiServer extends Component {
    private Closeable server;
    
    @Override
    public void onEnable() {
        try {
            String address = "http://0.0.0.0:"
                    + this.getMaster()
                            .getConfiguration()
                            .getAsInt(Configuration.KEY_PORT_API);
            
            DefaultResourceConfig resourceConfig = new DefaultResourceConfig(
                    ApiResource.class);
            this.server = SimpleServerFactory.create(address, resourceConfig);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onDisable() {
        try {
            this.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
