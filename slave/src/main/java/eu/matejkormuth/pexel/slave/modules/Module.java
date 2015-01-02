package eu.matejkormuth.pexel.slave.modules;

import eu.matejkormuth.pexel.commons.Component;
import eu.matejkormuth.pexel.slave.PexelSlave;

/**
 * @author Mato Kormuth
 * 
 */
public abstract class Module implements Component {
    private Object configuration;
    
    public Object getConfiguration() {
        return this.configuration;
    }
    
    void setConfiguration(final Object configuration) {
        this.configuration = configuration;
    }
    
    public ModuleManager getModuleManager() {
        return PexelSlave.getInstance().getComponent(ModuleManager.class);
    }
}
