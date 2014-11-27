package eu.matejkormuth.pexel.master.responders;

import eu.matejkormuth.pexel.commons.data.Profile;
import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.protocol.requests.DataProfileRequest;
import eu.matejkormuth.pexel.protocol.responses.DataProfileResponse;

/**
 * Class that responds to {@link Profile} requests.
 */
public class ProfileResponder {
    public DataProfileResponse onProfileRequest(final DataProfileRequest request) {
        return new DataProfileResponse(PexelMaster.getInstance()
                .getCaches()
                .getProfileCache()
                .get(request.uuid));
    }
}
