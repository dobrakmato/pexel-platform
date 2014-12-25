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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import eu.matejkormuth.pexel.commons.configuration.Configuration;
import eu.matejkormuth.pexel.master.MasterComponent;
import eu.matejkormuth.pexel.master.db.entities.ProfileEntity;

/**
 * Databse component on master.
 */
public class Database extends MasterComponent {
    protected String     url;
    protected String     username;
    protected String     password;
    
    protected Connection connection;
    
    public Database() {
        this.url = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_URL,
                        Configuration.Defaults.DATABASE_URL)
                .asString();
        this.username = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_USERNAME,
                        Configuration.Defaults.DATABASE_USERNAME)
                .asString();
        this.password = this.getConfiguration()
                .get(Configuration.Keys.KEY_DATABASE_PASSWORD,
                        Configuration.Defaults.DATABASE_PASSWORD)
                .asString();
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    @Override
    public void onEnable() {
        this.getLogger().info("Connecting to database server...");
        try {
            this.connection = DriverManager.getConnection(this.url, this.username,
                    this.password);
            this.prepeareStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void prepeareStatements() {
        // TODO Auto-generated method stub
        
    }
    
    public ProfileEntity getProfile(final UUID key) {
        // TODO Auto-generated method stub
        return null;
    }
}
