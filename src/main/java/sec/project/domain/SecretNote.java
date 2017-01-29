
package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class SecretNote extends AbstractPersistable<Long>{

    private String owner;
    private String title;
    private String content;

    public SecretNote() {
    }

    public SecretNote(String owner, String title, String content) {
        this.owner = owner;
        this.title = title;
        this.content = content;
    }
    
    

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.getId() + "\n" + this.owner + "\n" + this.title + "\n" + this.content; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
}
