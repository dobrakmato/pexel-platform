package eu.matejkormuth.pexel.commons;

import java.util.UUID;

import eu.matejkormuth.pexel.commons.math.Vector3d;

/**
 * {@link Location} implementation that is mutable.
 */
public class MutableLocation extends Location {
    private Vector3d   vector;
    private float      yaw;
    private float      pitch;
    private final UUID world;
    
    public MutableLocation(final UUID world) {
        super(world);
        this.world = world;
    }
    
    public MutableLocation(final Vector3d vector, final UUID world) {
        super(vector, world);
        this.vector = vector;
        this.world = world;
    }
    
    public MutableLocation(final double x, final double y, final double z,
            final UUID world) {
        super(x, y, z, world);
        this.world = world;
        this.vector = new Vector3d(x, y, z);
    }
    
    public MutableLocation(final double x, final double y, final double z,
            final float yaw, final float pitch, final UUID world) {
        super(x, y, z, yaw, pitch, world);
        this.world = world;
        this.vector = new Vector3d(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Location(this.vector.getX(), this.vector.getY(), this.vector.getZ(),
                this.yaw, this.pitch, this.world);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.vector.hashCode();
        hash = (int) (31 * hash + this.yaw);
        hash = (int) (31 * hash + this.pitch);
        hash = 31 * hash + this.world.hashCode();
        return hash;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) { return true; }
        
        if (obj == null) { return false; }
        
        if (obj instanceof MutableLocation) { return this.vector.equals(((MutableLocation) obj).vector)
                && this.yaw == ((MutableLocation) obj).yaw
                && this.pitch == ((MutableLocation) obj).pitch
                && this.world == ((MutableLocation) obj).world; }
        return false;
    }
    
    @Override
    public double getX() {
        return this.vector.getX();
    }
    
    @Override
    public double getY() {
        return this.vector.getY();
    }
    
    @Override
    public double getZ() {
        return this.vector.getZ();
    }
    
    @Override
    public Vector3d toVector() {
        return this.vector;
    }
    
    @Override
    public float getYaw() {
        return this.yaw;
    }
    
    @Override
    public float getPitch() {
        return this.pitch;
    }
    
    @Override
    public UUID getWorld() {
        return this.world;
    }
    
    @Override
    public String toString() {
        return "Location [vector=" + this.vector + ", yaw=" + this.yaw + ", pitch="
                + this.pitch + ", world=" + this.world + "]";
    }
    
    @Override
    public MutableLocation add(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        this.vector.add(location.toVector());
        return this;
    }
    
    @Override
    public MutableLocation subtract(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        this.vector = this.vector.subtract(location.toVector());
        return this;
    }
    
    @Override
    public MutableLocation multiply(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        this.vector = this.vector.multiply(location.toVector());
        return this;
    }
    
    @Override
    public MutableLocation divide(final Location location) {
        if (this.world != location.getWorld()) { throw new RuntimeException(
                "Can't add two locations of different worlds!"); }
        this.vector = this.vector.divide(location.toVector());
        return this;
    }
    
    @Override
    public MutableLocation add(final double d) {
        this.vector = this.vector.add(d);
        return this;
    }
    
    @Override
    public MutableLocation subtract(final double d) {
        this.vector = this.vector.subtract(d);
        return this;
    }
    
    @Override
    public MutableLocation multiply(final double d) {
        this.vector = this.vector.multiply(d);
        return this;
    }
    
    @Override
    public MutableLocation divide(final double d) {
        this.vector = this.vector.divide(d);
        return this;
    }
    
    @Override
    public MutableLocation add(final double x, final double y, final double z) {
        this.vector = this.vector.add(x, y, z);
        return this;
    }
    
    @Override
    public MutableLocation subtract(final double x, final double y, final double z) {
        this.vector = this.vector.subtract(x, y, z);
        return this;
    }
    
    @Override
    public MutableLocation multiply(final double x, final double y, final double z) {
        this.vector = this.vector.multiply(x, y, z);
        return this;
    }
    
    @Override
    public MutableLocation divide(final double x, final double y, final double z) {
        this.vector = this.vector.divide(x, y, z);
        return this;
    }
    
    @Override
    public MutableLocation add(final Vector3d amount) {
        this.vector = this.vector.add(amount);
        return this;
    }
    
    @Override
    public MutableLocation subtract(final Vector3d amount) {
        this.vector = this.vector.subtract(amount);
        return this;
    }
    
    @Override
    public MutableLocation multiply(final Vector3d amount) {
        this.vector = this.vector.multiply(amount);
        return this;
    }
    
    @Override
    public MutableLocation divide(final Vector3d amount) {
        this.vector = this.vector.divide(amount);
        return this;
    }
    
    public Location toImutable() {
        return new Location(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch,
                this.world);
    }
}
