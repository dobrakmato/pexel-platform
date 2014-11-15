package eu.matejkormuth.pexel.network.requests;

import eu.matejkormuth.pexel.network.Callback;
import eu.matejkormuth.pexel.network.EmptyAsyncRequest;
import eu.matejkormuth.pexel.network.responses.ServerStatusResponse;

public class ServerStatusRequest extends EmptyAsyncRequest {
    public ServerStatusRequest(final Callback<ServerStatusResponse> callback) {
        super(callback);
    }
}
