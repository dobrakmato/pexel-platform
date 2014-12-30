package eu.matejkormuth.pexel.slave.modules

import eu.matejkormuth.pexel.commons.Component
import eu.matejkormuth.pexel.slave.PexelSlave

/**
 *
 */
abstract class Module implements Component {
    def configuration // Anonymouse type.
    
    ModuleManager getModuleManager() {
        return PexelSlave.getInstance().getComponent(ModuleManager.class);
    }
}
