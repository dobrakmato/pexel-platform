package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;
import java.util.UUID;

import eu.matejkormuth.pexel.network.AsyncRequest;
import eu.matejkormuth.pexel.network.Callback;
import eu.matejkormuth.pexel.protocol.responses.InMatchmakingStatusResponse;

public class OutMatchmakingGameStatusRequest extends AsyncRequest {
    public UUID gameId;
    
    public OutMatchmakingGameStatusRequest(
            final Callback<InMatchmakingStatusResponse> callback, final UUID gameId) {
        super(callback);
        this.gameId = gameId;
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(16)
                .putLong(this.gameId.getMostSignificantBits())
                .putLong(this.gameId.getLeastSignificantBits());
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.gameId = new UUID(buffer.getLong(), buffer.getLong());
    }
    
}
