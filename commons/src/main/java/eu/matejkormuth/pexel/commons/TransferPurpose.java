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

import java.util.HashMap;
import java.util.Map;

/**
 * Enum that specifies transfer purpose.
 */
public enum TransferPurpose {
    /**
     * Installing on slave purpose. File will be installed on slave server.
     */
    INSTALLING_ON_SLAVE(0);
    
    private byte b;
    
    private TransferPurpose(final int i) {
        this.b = (byte) i;
    }
    
    public byte getByte() {
        return this.b;
    }
    
    public static TransferPurpose fromByte(final byte b) {
        return mapping.get(b);
    }
    
    private static Map<Byte, TransferPurpose> mapping = new HashMap<Byte, TransferPurpose>();
    static {
        for (TransferPurpose tp : TransferPurpose.values()) {
            TransferPurpose.mapping.put(tp.b, tp);
        }
    }
}
