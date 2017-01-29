
package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.SecretNote;

public interface SecretNoteRepository extends JpaRepository<SecretNote, Long>{
    
    List<SecretNote> findByOwner(String owner);
    
}
