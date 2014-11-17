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
package eu.matejkormuth.pexel.commons;

/**
 * Interface that represents component that can be enabled, disabled and have own {@link Logger}.
 */
public abstract class Component {
    protected Logger logger;
    
    /**
     * Called when internal logic of component (not buisness logic) should be initialized.
     * 
     * @param parentLogger
     *            object that contains parent logger.
     */
    public void _initLogger(final LoggerHolder parentLogger) {
        this.logger = parentLogger.getLogger().getChild(this.getClass().getSimpleName());
    }
    
    /**
     * Returns child logger for this component derived from master logger.
     * 
     * @return child logger
     */
    public Logger getLogger() {
        return this.logger;
    }
    
    public void onEnable() {
    };
    
    public void onDisable() {
    };
}
