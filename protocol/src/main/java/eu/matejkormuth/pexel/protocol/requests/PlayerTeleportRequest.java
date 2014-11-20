package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;
import java.util.UUID;

import eu.matejkormuth.pexel.network.MessageTarget;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.network.ServerType;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

/**
 * Cross server teleportation request.
 */
@MessageTarget(ServerType.MASTER)
public class PlayerTeleportRequest extends Request {
    public String targetServer;
    public UUID   uuid;
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(this.targetServer.length() + 16)
                .putLong(this.uuid.getMostSignificantBits())
                .putLong(this.uuid.getLeastSignificantBits())
                .put(this.targetServer.getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.uuid = new UUID(buffer.getLong(), buffer.getLong());
        this.targetServer = new String(ByteBuffer.wrap(buffer.array(), 16,
                buffer.array().length - 16)
                .slice()
                .array(), PexelProtocol.CHARSET);
    }
    
}
