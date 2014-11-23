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
package eu.matejkormuth.pexel.slave.bukkit.stats;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * PHP klient pre StatsClient.
 * 
 * @author Mato Kormuth
 * 
 */
public class PHPStatsClient extends StatsClient {
    //kluc a server
    private final String apiKey;
    private final String server;
    
    /**
     * Creates new instance of stats client. Connects to specified stats server with specified api key.
     * 
     * @param apiKey
     *            secret api key.
     * @param server
     *            server path.
     */
    public PHPStatsClient(final String apiKey, final String server) {
        this.server = server;
        this.apiKey = apiKey;
    }
    
    @Override
    public void setStat(final String pid, final String stat, final int value) {
        this.sendRequestAsync("setStat", "pid=" + pid + "&stat=" + stat + "&value="
                + value);
    }
    
    @Override
    public void incrementStat(final String pid, final String stat, final int amount) {
        this.sendRequestAsync("incrementStat", "pid=" + pid + "&stat=" + stat
                + "&amount=" + amount);
    }
    
    @Override
    public void decrementStat(final String pid, final String stat, final int amount) {
        this.sendRequestAsync("decrementStat", "pid=" + pid + "&stat=" + stat
                + "&amount=" + amount);
    }
    
    @Override
    public void resetStats(final String pid) {
        this.sendRequestAsync("resetStats", "pid=" + pid);
    }
    
    @Override
    public void registerUser(final String pid, final String name, final String email) {
        this.sendRequestAsync("registerUser", "pid=" + pid + "&name=" + name + "&email="
                + email);
    }
    
    private void sendRequestAsync(final String action, final String params) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(this.server
                    + "/api.php").openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes("apikey=" + this.apiKey + "&action=" + action + "&" + params);
            wr.flush();
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
