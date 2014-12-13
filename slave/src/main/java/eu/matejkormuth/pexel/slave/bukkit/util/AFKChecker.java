package eu.matejkormuth.pexel.slave.bukkit.util;

import java.util.HashMap;
import java.util.Map;

import eu.matejkormuth.pexel.commons.Location;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.PlayerHolder;

/**
 * Class that is used for checking when is player AFK.
 */
public class AFKChecker {
    private final int                   time;
    private final BukkitTimer           timer;
    private final PlayerHolder          playerHolder;
    private final Map<Player, AFKEntry> entries = new HashMap<Player, AFKEntry>();
    
    public AFKChecker(final int seconds, final PlayerHolder playerHolder) {
        this.time = seconds;
        this.playerHolder = playerHolder;
        this.timer = new BukkitTimer(20, new Runnable() {
            @Override
            public void run() {
                AFKChecker.this.check();
            }
        });
        this.timer.start();
    }
    
    protected void check() {
        long checkTime = System.currentTimeMillis();
        
        for (Player p : this.playerHolder.getPlayers()) {
            AFKEntry entry = this.entries.get(p);
            
            // Check for kicks
            if (System.currentTimeMillis() > entry.lastMovementTime + this.time) {
                p.kick("You were kicked, because you were AFK. If it was during a competitive match, you migth be penalized.");
                this.entries.remove(p);
                continue;
            }
            else if (!p.isOnline()) {
                this.entries.remove(p);
            }
            
            // Update last movement
            if (!entry.lastLoc.equals(p.getLocation())) {
                entry.lastLoc = p.getLocation();
                entry.lastMovementTime = checkTime;
            }
        }
    }
    
    public void start() {
        this.timer.start();
    }
    
    public void reset() {
        this.entries.clear();
        this.timer.stop();
    }
    
    public class AFKEntry {
        public Location lastLoc;
        public long     lastMovementTime;
    }
}
