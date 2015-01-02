package eu.matejkormuth.pexel.slave.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;

import eu.matejkormuth.pexel.slave.SlaveComponent;
import eu.matejkormuth.pexel.slave.events.player.PlayerInteractBlockEvent;

/**
 * @author Mato Kormuth
 * 
 */
public class ModuleManager extends SlaveComponent {
    List<Module>              modules = new ArrayList<Module>();
    Map<String, ToolListener> tools   = Maps.newHashMap();
    
    @Override
    public void onEnable() {
        // Load all modules.
        
        // Enable all modules.
        for (Module m : this.modules) {
            this.enableModule(m);
        }
    }
    
    @Override
    public void onDisable() {
        // Disable all modules.
        for (Module m : this.modules) {
            this.disableModule(m);
        }
    }
    
    @Subscribe
    public void onPlayerIteract(final PlayerInteractBlockEvent event) {
        String name = event.getItemInHand().getDisplayName();
        if (this.tools.containsKey(name)) {
            try {
                this.tools.get(name).onUse(event.getBlockPos(), event.getUsedButton(),
                        event.getPlayer());
            } catch (Exception exception) {
                this.getLogger().error(exception.toString());
            }
        }
    }
    
    public void registerModule(final Module e) {
        this.modules.add(e);
    }
    
    public void registerTool(final String name, final ToolListener listener) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(listener);
        this.tools.put(name, listener);
    }
    
    private void enableModule(final Module m) {
        try {
            m.setConfiguration(new Object()); // Load configuration
            m.onEnable();
        } catch (Exception exception) {
            this.getLogger().error(
                    "Can't enable " + m.getClass().getName() + " because: "
                            + exception.toString());
        }
    }
    
    private void disableModule(final Module m) {
        try {
            m.getConfiguration(); // Save configuration
            m.onDisable();
        } catch (Exception exception) {
            this.getLogger().error(
                    "Can't disable " + m.getClass().getName() + " because: "
                            + exception.toString());
        }
    }
    
}
