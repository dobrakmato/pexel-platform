package eu.matejkormuth.pexel.protocol.responses;

import java.nio.ByteBuffer;

import eu.matejkormuth.pexel.commons.matchmaking.GameState;
import eu.matejkormuth.pexel.network.Response;
import eu.matejkormuth.pexel.protocol.PexelProtocol;

public class InMatchmakingStatusResponse extends Response {
    public int       freeSlots;
    public int       maximumSlots;
    public GameState state;
    public int       playerCount;
    
    @Override
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.allocate(12 + this.state.name().length())
                .putInt(this.freeSlots)
                .putInt(this.maximumSlots)
                .putInt(this.playerCount)
                .put(this.state.name().getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        this.freeSlots = buffer.getInt();
        this.maximumSlots = buffer.getInt();
        this.playerCount = buffer.getInt();
        this.state = GameState.valueOf(new String(buffer.slice().array(),
                PexelProtocol.CHARSET));
    }
    
}
