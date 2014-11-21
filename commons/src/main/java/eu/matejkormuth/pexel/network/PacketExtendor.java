package eu.matejkormuth.pexel.network;

import eu.matejkormuth.pexel.commons.Asynchronous;
import eu.matejkormuth.pexel.commons.Commitable;

/**
 * Class that maintains sending and receveing packets on updated objects.
 */
public abstract class PacketExtendor<Rq extends Request, Rs extends Response> implements
        Commitable {
    private ServerInfo         destination;
    private ServerType         side;
    private final Callback<Rs> callback;
    
    public PacketExtendor(final ServerType side) {
        if (side == ServerType.MASTER) {
            this.destination = null;
        }
        else {
            this.destination = ((SlaveServer) ServerInfo.localServer()).getMasterServerInfo();
        }
        
        this.callback = new Callback<Rs>() {
            @Override
            public void onResponse(final Rs response) {
                PacketExtendor.this.onResponse(response);
            }
        };
    }
    
    @Override
    public void commit() {
        if (this.side != ServerType.MASTER) { throw new RuntimeException(
                "You must specify target when using send() method."); }
        
        Rq request = this.onRequest();
        this.destination.sendRequest(request);
    }
    
    public void send(final ServerInfo target) {
        if (this.side != ServerType.SLAVE) { throw new RuntimeException(
                "You must specify target when using send() method."); }
        
        Rq request = this.onRequest();
        target.sendRequest(request);
    }
    
    public ServerInfo getDestination() {
        return this.destination;
    }
    
    public ServerType getSide() {
        return this.side;
    }
    
    public Callback<Rs> getCallback() {
        return this.callback;
    }
    
    @Asynchronous
    public abstract void onResponse(final Rs response);
    
    @Asynchronous
    public abstract Rq onRequest();
}
