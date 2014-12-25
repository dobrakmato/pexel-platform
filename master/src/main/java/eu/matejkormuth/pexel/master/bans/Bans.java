package eu.matejkormuth.pexel.master.bans;

import eu.matejkormuth.pexel.commons.bans.Ban;
import eu.matejkormuth.pexel.master.db.entities.BanEntity;

/**
 *
 */
public class Bans {
    public static Ban createBan() {
        return new BanEntity();
    }
}
