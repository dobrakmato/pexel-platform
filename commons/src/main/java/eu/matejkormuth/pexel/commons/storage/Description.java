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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import eu.matejkormuth.pexel.commons.Revision;
import eu.matejkormuth.pexel.commons.annotations.JsonType;

/**
 * Class that specifies default fields for every description.
 */
@JsonType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Description {
    private String   author;
    private String   name;
    private Revision revision;
    private String[] tags;
    
    /**
     * Only for JAXB.
     */
    public Description() {
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(final String author) {
        this.author = author;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Revision getRevision() {
        return this.revision;
    }
    
    public void setRevision(final Revision revision) {
        this.revision = revision;
    }
    
    public String[] getTags() {
        return this.tags;
    }
    
    public void setTags(final String[] tags) {
        this.tags = tags;
    }
    
    public void save(final File f) {
        this.save(f, false);
    }
    
    public void save(final File f, final boolean prettyPrint) {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = context.createMarshaller();
            if (prettyPrint) {
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            }
            marshaller.marshal(this, f);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    public static Description load(final File f) {
        try {
            JAXBContext context = JAXBContext.newInstance(Description.class);
            return (Description) context.createUnmarshaller().unmarshal(f);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
