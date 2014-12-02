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
public abstract class ServerComponent implements Component {
    /**
     * Logger object of this componenet.
     */
    protected Logger               logger;
    /**
     * Configuration section of this component.
     */
    protected ConfigurationSection config;
    
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
     * Called when internal logic of component (not buisness logic) should be initialized.
     * 
     * @param parentConfiguration
     *            configuration of parent, who contains this component.
     */
    public void _initConfig(final Configuration parentConfiguration) {
        this.config = parentConfiguration.getSection(this.getClass().getCanonicalName());
    }
    
    /**
     * Returns child {@link Logger} for this component derived from master logger.
     * 
     * @return child logger
     */
    public Logger getLogger() {
        return this.logger;
    }
    
    /**
     * Returns {@link ConfigurationSection} of this component.
     * 
     * @return configuration section of this component.
     */
    public ConfigurationSection getConfiguration() {
        return this.config;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEnable() {
    }
}
