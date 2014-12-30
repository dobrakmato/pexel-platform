package eu.matejkormuth.pexel.master.db.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.matejkormuth.pexel.commons.bans.Ban;
import eu.matejkormuth.pexel.commons.bans.BanAuthor;
import eu.matejkormuth.pexel.commons.bans.Bannable;
import eu.matejkormuth.pexel.commons.bans.BannableIdImpl;
import eu.matejkormuth.pexel.commons.bans.PlayerBanAuthor;
import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.master.db.SoftDeletesImmutableEntity;

/**
 * {@link Ban} database entity.
 */
@Entity
@Table(name = "bans")
public class BanEntity extends SoftDeletesImmutableEntity implements Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int     id;
    @Column(name = "permanent")
    private boolean permanent;
    @Column(name = "reason", nullable = true, length = 255)
    private String  reason;
    @Column(name = "length")
    private long    length;
    @Column(name = "createdAt")
    private long    createdAt;
    @Column(name = "author")
    private UUID    author;
    @Column(name = "player")
    private UUID    player;
    @Column(name = "partId", nullable = false, length = 255)
    private String  networkPartId;
    
    @Override
    public boolean isPermanent() {
        return this.permanent;
    }
    
    @Override
    public BanAuthor getAuthor() {
        return new PlayerBanAuthor(PexelMaster.getInstance().getProfile(this.author));
    }
    
    @Override
    public Profile getPlayer() {
        return PexelMaster.getInstance().getProfile(this.player);
    }
    
    @Override
    public Bannable getNetworkPart() {
        return new BannableIdImpl(this.networkPartId);
    }
    
    @Override
    public String getReason() {
        return this.reason;
    }
    
    @Override
    public long getLength() {
        return this.length;
    }
    
    @Override
    public long getCreatedAt() {
        return this.createdAt;
    }
    
    @Override
    public long getExpireAt() {
        return this.createdAt + this.length;
    }
}
