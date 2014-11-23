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
package eu.matejkormuth.pexel.slave.bukkit.arenas;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that this field represents arena option. Apply on fields in {@link AbstractArena} or {@link AdvancedArena}
 * to flag them as arena 'options'.
 * 
 * @deprecated Replaced by {@link MapData}. Try to use {@link MapData} where possible. May be removed in future.
 */
@Target({ java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface ArenaOption {
    /**
     * Name of option.
     * 
     * @return the name of option
     */
    String name();
    
    /**
     * Whether the option should be persistent (saved and loaded when arena is setting up). Default value is true.
     * 
     * @return flag if the value should be persistent
     */
    boolean persistent() default true;
}
