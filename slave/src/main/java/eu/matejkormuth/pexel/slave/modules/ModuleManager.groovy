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
package eu.matejkormuth.pexel.slave.modules

import com.google.common.eventbus.Subscribe

import eu.matejkormuth.pexel.slave.SlaveComponent
import eu.matejkormuth.pexel.slave.events.player.PlayerInteractBlockEvent

/**
 *
 */
class ModuleManager extends SlaveComponent {
    def toolsMap = [:]
    def modules = []
    File moduleConfigBasePath
    ModuleConfigurationLoader loader;

    @Override
    public void onDisable() {
        // Save configurations.
        this.modules.each { module ->
            this.disableModule(module)
        }
    }

    @Override
    public void onEnable() {
        this.loader = new ModuleConfigurationLoader(basePath: this.moduleConfigBasePath);
        // Load all modules
        this.loadModules();

        // Enable all modules
        this.modules.each { module ->
            this.enableModule(module)
        }
    }

    void registerModule(Module module) {
        this.modules.add(module);
    }

    void registerTool(String name, Closure closure) {
        this.toolsMap.put(name, closure);
    }

    @Subscribe
    protected onInteractBlockOrAir(PlayerInteractBlockEvent event) {
        String name = event.getItemInHand().getDisplayName();
        if(this.toolsMap.containsKey(name)) {
            Closure c = this.toolsMap.get(name);
            c.call(event.getBlockPos(), event.getUsedButton(), event.getPlayer());
        }
    }

    private void enableModule(Module module) {
        this.logger.info("Enabling module " + module.getClass().getName());
        try {
            module.setConfiguration(this.loader.load(module.getClass().getName()));
            module.onEnable();
        } catch (Exception ex) {
            this.logger.error("Can't enable module! " + ex.toString());
        }
    }

    private void disableModule(Module module) {
        this.logger.info("Disabling module " + module.getClass().getName());
        try {
            this.loader.save(module.getClass().getName(), module.getConfiguration());
            module.onDisable();
        } catch (Exception ex) {
            this.logger.error("Can't disable module! " + ex.toString());
        }
    }
}
