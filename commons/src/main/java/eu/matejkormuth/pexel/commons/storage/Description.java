package eu.matejkormuth.pexel.commons.storage;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Global description.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Description {
    private String author;
    private String name;
    private String revision;
    
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
    
    public String getRevision() {
        return this.revision;
    }
    
    public void setRevision(final String revision) {
        this.revision = revision;
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
