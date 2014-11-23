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
package eu.matejkormuth.pexel.slave.bukkit.cinematics;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface specifikujuci, ze dana trieda je V3Meta.
 * 
 * @author Mato Kormuth
 * 
 */
public interface V3Meta {
    /**
     * Typ meta.
     */
    public V3MetaType getMetaType();
    
    /**
     * Zapise meta do streamu.
     * 
     * @param stream
     */
    public void writeMeta(DataOutputStream stream) throws IOException;
    
    /**
     * Vrati ciselny typ META.
     * 
     * @return
     */
    public int getType();
}
