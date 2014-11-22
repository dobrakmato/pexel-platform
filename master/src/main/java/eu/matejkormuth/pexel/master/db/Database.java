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
package eu.matejkormuth.pexel.master.db;

import java.sql.Connection;

import eu.matejkormuth.pexel.commons.Configuration;
import eu.matejkormuth.pexel.master.MasterComponent;

/**
 * Databse component on master.
 */
public class Database extends MasterComponent {
    protected String     host;
    protected String     username;
    protected String     password;
    protected String     database;
    
    protected Connection connection;
    
    public Database() {
        this.host = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_HOST,
                        Configuration.Defaults.DATABASE_HOST)
                .asString();
        this.username = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_USERNAME,
                        Configuration.Defaults.DATABASE_USERNAME)
                .asString();
        this.password = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_PASSWORD,
                        Configuration.Defaults.DATABASE_PASSWORD)
                .asString();
        this.database = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_DB,
                        Configuration.Defaults.DATABASE_DB)
                .asString();
    }
    
    @Override
    public void onEnable() {
        this.getLogger().info("Connecting to database server...");
        
    }
}
