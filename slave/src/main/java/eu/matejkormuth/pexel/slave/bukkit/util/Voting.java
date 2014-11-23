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
package eu.matejkormuth.pexel.slave.bukkit.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import eu.matejkormuth.pexel.slave.bukkit.Pexel;

/**
 * Class used for voting.
 */
public abstract class Voting {
    /**
     * Amount of ticks in one second.
     */
    private static final long       ONE_SECOND      = 20L;
    
    private List<Player>            voters;
    private final Map<Player, Vote> votes           = new HashMap<Player, Vote>();
    private final String            voteSubject;
    private long                    lastInteraction = Long.MAX_VALUE;
    private long                    timeout         = 20 * 5;
    private boolean                 canVoteOnlyOnce = true;
    private int                     taskId          = 0;
    
    public Voting(final String voteSubject) {
        this.voteSubject = voteSubject;
    }
    
    /**
     * Starts this vote.
     * 
     * @param voters
     *            players that should be able to vote
     * @param invoker
     *            player that invoked the vote
     */
    public void invoke(final List<Player> voters, final Player invoker) {
        this.voters = voters;
        this.taskId = Pexel.getScheduler().scheduleSyncRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Voting.this.timeout();
            }
        }, 0L, Voting.ONE_SECOND);
        this.startVote(invoker);
        this.lastInteraction = System.currentTimeMillis();
    }
    
    /**
     * Called when voting should timeout.
     */
    protected void timeout() {
        if (this.lastInteraction + this.timeout < System.currentTimeMillis()) {
            Pexel.getScheduler().cancelTask(this.taskId);
            this.onVoteFailed();
        }
    }
    
    /**
     * Sends message to all voters.
     */
    private void startVote(final Player invoker) {
        this.broadcast("Player " + invoker.getName() + " started the vote for "
                + this.voteSubject + ".");
    }
    
    public void broadcast(final String message) {
        for (Player p : this.voters)
            p.sendMessage(ChatColor.GOLD + "[VOTE] " + message);
    }
    
    /**
     * Votes positively for specified player.
     * 
     * @param voter
     *            player that votes for subject
     */
    public void vote(final Player voter, final Vote vote) {
        if (!this.voters.contains(voter))
            throw new RuntimeException("Invalid voter! Specified voter can't vote.");
        if (this.votes.containsKey(voter) && this.canVoteOnlyOnce)
            throw new RuntimeException("Invalid vote! One player can vote only once!");
        
        this.votes.put(voter, vote);
        this.broadcastState();
        this.processEnd();
        
        this.lastInteraction = System.currentTimeMillis();
    }
    
    /**
     * Check for voting end.
     */
    private void processEnd() {
        int yesVotes = 0;
        for (Vote value : this.votes.values())
            if (value == Vote.YES)
                yesVotes++;
        
        if (yesVotes == this.voters.size())
            this.onVoteSucceeded();
        else if (yesVotes > (this.voters.size() / 2))
            this.onVoteSucceeded();
        else
            this.onVoteFailed();
    }
    
    /**
     * Broadcast vote state to all players.
     */
    private void broadcastState() {
        int yesVotes = 0;
        for (Vote value : this.votes.values())
            if (value == Vote.YES)
                yesVotes++;
        
        this.broadcast(yesVotes + "/" + this.voters.size() + " players voted for "
                + this.voteSubject + "!");
    }
    
    // Abstract functions
    
    /**
     * Called when vote failed.
     */
    public abstract void onVoteFailed();
    
    /**
     * Called when vote succeeded.
     */
    public abstract void onVoteSucceeded();
    
    // Getters and setters
    
    public long getTimeout() {
        return this.timeout;
    }
    
    public void setTimeout(final long timeout) {
        this.timeout = timeout;
    }
    
    public boolean canVoteOnlyOnce() {
        return this.canVoteOnlyOnce;
    }
    
    public void setCanVoteOnlyOnce(final boolean canVoteOnlyOnce) {
        this.canVoteOnlyOnce = canVoteOnlyOnce;
    }
}
