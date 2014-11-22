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
package eu.matejkormuth.pexel.master.matchmaking;

import java.util.Collection;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingRequest;
import eu.matejkormuth.pexel.master.MasterComponent;
import eu.matejkormuth.pexel.master.PexelMaster;

/**
 * Master component class that is used for matchamking.
 */
public class Matchmaking extends MasterComponent {
    private MatchmakingProvider provider;
    private ScheduledFuture<?>  future;
    
    public Matchmaking(final MatchmakingProvider provider) {
        this.provider = provider;
    }
    
    public void setProvider(final MatchmakingProvider provider) {
        // TODO: Only one provider now.
        this.provider = provider;
    }
    
    public void registerRequest(final MatchmakingRequest request) {
        this.provider.addRequest(request);
    }
    
    public void cancelRequest(final MatchmakingRequest request) {
        this.provider.cancelRequest(request);
    }
    
    public Collection<MatchmakingGameImpl> getGames() {
        return this.provider.getGames();
    }
    
    @Override
    public void onEnable() {
        Preconditions.checkNotNull(this.provider,
                "Set matchmaking provider before enabling matchmaking.");
        
        this.future = PexelMaster.getInstance()
                .getScheduler()
                .scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Matchmaking.this.doMatchmaking();
                    }
                }, 0L, 2L, TimeUnit.SECONDS);
    }
    
    @Override
    public void onDisable() {
        this.future.cancel(true);
    }
    
    protected void doMatchmaking() {
        this.provider.doMatchmaking();
    }
}
