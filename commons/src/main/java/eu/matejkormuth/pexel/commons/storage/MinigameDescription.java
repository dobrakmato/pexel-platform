package eu.matejkormuth.pexel.commons.storage;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Description of minigame.
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class MinigameDescription extends Description {
    private String sourceUrl;
    
    /**
     * Only for JAXB.
     */
    public MinigameDescription() {
    }
    
    public String getSourceUrl() {
        return this.sourceUrl;
    }
    
    public void setSourceUrl(final String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    
    public static MinigameDescription load(final File f) {
        try {
            JAXBContext context = JAXBContext.newInstance(MinigameDescription.class);
            return (MinigameDescription) context.createUnmarshaller().unmarshal(f);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
