package eu.matejkormuth.pexel.protocol.requests;

import java.nio.ByteBuffer;
import java.util.Set;

import eu.matejkormuth.pexel.commons.Providers;
import eu.matejkormuth.pexel.commons.matchmaking.MatchmakingGame;
import eu.matejkormuth.pexel.network.Request;
import eu.matejkormuth.pexel.protocol.PexelProtocol;
import eu.matejkormuth.pexel.protocol.ServerSoftware;

/**
 * Reports minigames and maps on this server. Sent by slave server after connecting to master.
 */
public class ServerConfigurationRequest extends Request {
    public Set<String>          minigames;
    public Set<MatchmakingGame> games;
    public ServerSoftware       software;
    
    static class ServerConfiguration {
        public Set<String>          minigames;
        public Set<MatchmakingGame> games;
        public ServerSoftware       software;
        
        public ServerConfiguration from(
                final ServerConfigurationRequest serverConfigurationRequest) {
            this.minigames = serverConfigurationRequest.minigames;
            this.games = serverConfigurationRequest.games;
            this.software = serverConfigurationRequest.software;
            return this;
        }
        
        public void apply(final ServerConfigurationRequest serverConfigurationRequest) {
            serverConfigurationRequest.minigames = this.minigames;
            serverConfigurationRequest.games = this.games;
            serverConfigurationRequest.software = this.software;
        }
    }
    
    @Override
    public ByteBuffer toByteBuffer() {
        String json = Providers.JSON.toJson(new ServerConfiguration().from(this));
        return ByteBuffer.allocate(json.length()).put(
                json.getBytes(PexelProtocol.CHARSET));
    }
    
    @Override
    public void fromByteBuffer(final ByteBuffer buffer) {
        ServerConfiguration conf = Providers.JSON.fromJson(new String(buffer.array(),
                PexelProtocol.CHARSET), ServerConfiguration.class);
        conf.apply(this);
    }
}
