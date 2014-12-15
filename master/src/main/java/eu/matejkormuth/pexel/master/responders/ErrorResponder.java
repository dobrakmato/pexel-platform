// @formatter:off
/*
 * Pexel Project - Minecraft minigame server platform. 
 * Copyright (C) 2014 Matej Kormuth <http://www.matejkormuth.eu>
 * 
 * This file is part of Pexel.
 * 
 * Pexel is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Pexel is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
// @formatter:on
package eu.matejkormuth.pexel.master.responders;

import com.google.common.base.Optional;

import eu.matejkormuth.pexel.master.PexelMaster;
import eu.matejkormuth.pexel.network.ServerInfo;
import eu.matejkormuth.pexel.protocol.requests.InPlayerUnresolvableError;

/**
 * Class that responds to {@link InPlayerUnresolvableError}.
 */
public class ErrorResponder {
    public void onError(final InPlayerUnresolvableError msg) {
        // We don't resolve problems. Just connect player to limbo.
        Optional<ServerInfo> limbo = PexelMaster.getInstance().getLimboServer();
        if (limbo.isPresent()) {
            PexelMaster.getInstance()
                    .getProxy()
                    .connect(
                            PexelMaster.getInstance().getProxy().getPlayer(msg.playerId),
                            limbo.get());
        }
        // Log error occurance
        PexelMaster.getInstance()
                .getLogger()
                .error("Unresolvable error happend: " + msg.error);
    }
}
