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
package eu.matejkormuth.pexel.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum that specifies received / sent payload data type.
 */
public enum PayloadDataType {
    REQUEST(0),
    RESPONSE(1),
    OTHER(127);
    
    private int type;
    
    private PayloadDataType(final int type) {
        this.type = type;
    }
    
    public byte getType() {
        return (byte) this.type;
    }
    
    public static PayloadDataType fromType(final byte type) {
        return PayloadDataType.type_mapping.get(type);
    }
    
    private static Map<Byte, PayloadDataType> type_mapping = new HashMap<Byte, PayloadDataType>();
    
    static {
        for (PayloadDataType type : PayloadDataType.values()) {
            PayloadDataType.type_mapping.put(type.getType(), type);
        }
    }
}
