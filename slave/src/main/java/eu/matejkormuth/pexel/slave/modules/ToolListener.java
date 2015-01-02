package eu.matejkormuth.pexel.slave.modules;

import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.commons.math.Vector3d;

/**
 * @author Mato Kormuth
 * 
 */
public interface ToolListener {
    void onUse(Vector3d blockPos, MouseButton usedButton, Player sender);
}
