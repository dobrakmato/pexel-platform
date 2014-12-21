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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.matejkormuth.pexel.commons.configuration.Configuration;
import eu.matejkormuth.pexel.commons.configuration.ConfigurationSection;

/**
 * Interface that represents component that can be enabled, disabled and have own {@link Logger}.
 */
public abstract class AbstractComponent implements Component {
    /**
     * Logger object of this componenet.
     */
    protected Logger               logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Configuration section of this component.
     */
    protected ConfigurationSection config;
    
    /**
     * Called when internal logic of component (not buisness logic) should be initialized.
     * 
     * @param parentConfiguration
     *            configuration of parent, who contains this component.
     */
    protected void _initConfig(final Configuration parentConfiguration) {
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
