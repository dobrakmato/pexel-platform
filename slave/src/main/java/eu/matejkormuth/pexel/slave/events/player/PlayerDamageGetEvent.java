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
package eu.matejkormuth.pexel.slave.events.player;

import eu.matejkormuth.pexel.commons.DamageCause;
import eu.matejkormuth.pexel.commons.Player;
import eu.matejkormuth.pexel.slave.events.Cancellable;

/**
 * Fired when player entity gets damage.
 */
public class PlayerDamageGetEvent extends PlayerEvent implements Cancellable {
    private boolean           cancelled = false;
    private double            damage;
    private final DamageCause cause;
    
    public PlayerDamageGetEvent(final Player player, final double damage,
            final DamageCause cause) {
        super(player);
        this.damage = damage;
        this.cause = cause;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    @Override
    public boolean isCanceled() {
        return this.cancelled;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public DamageCause getCause() {
        return this.cause;
    }
    
    public void setDamage(final double damage) {
        this.damage = damage;
    }
}
