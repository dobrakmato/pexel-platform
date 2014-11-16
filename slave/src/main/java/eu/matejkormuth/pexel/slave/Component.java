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
package eu.matejkormuth.pexel.slave;

import eu.matejkormuth.pexel.commons.Logger;

/**
 * Interface that represents component in MasterServer.
 */
public abstract class Component {
    private PexelSlave slave;
    private Logger     logger;
    
    /**
     * Returns current {@link PexelMaster} instance.
     */
    public PexelSlave getSlave() {
        return this.slave;
    }
    
    /**
     * Returns child logger for this component derived from master logger.
     * 
     * @return child logger
     */
    public Logger getLogger() {
        if (this.logger == null) {
            return this.logger = this.slave.getLogger().getChild(
                    this.getClass().getSimpleName());
        }
        else {
            return this.logger;
        }
    }
    
    public void onEnable() {
    };
    
    public void onDisable() {
    };
}
