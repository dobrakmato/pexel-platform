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
package eu.matejkormuth.pexel.commons.storage;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eu.matejkormuth.pexel.commons.annotations.JsonType;

/**
 * Description of minigame.
 */
@JsonType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MinigameDescriptor extends Description implements Descriptor {
    private String         sourceUrl;
    private transient File file;
    
    /**
     * Only for JAXB.
     */
    public MinigameDescriptor() {
    }
    
    public String getSourceUrl() {
        return this.sourceUrl;
    }
    
    public void setSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    
    public static MinigameDescriptor load(final File f) {
        try {
            JAXBContext context = JAXBContext.newInstance(MinigameDescriptor.class);
            MinigameDescriptor md = (MinigameDescriptor) context.createUnmarshaller()
                    .unmarshal(f);
            md.file = f;
            return md;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public byte[] getData() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static MinigameDescriptor load(final InputStream remoteDescriptorFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(MinigameDescriptor.class);
            return (MinigameDescriptor) context.createUnmarshaller().unmarshal(
                    remoteDescriptorFile);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    
    public File getJar() {
        return new File(this.file.getAbsolutePath() + "/" + this.getName() + ".jar");
    }
}
