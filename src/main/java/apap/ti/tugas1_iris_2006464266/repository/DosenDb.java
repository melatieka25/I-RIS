package apap.ti.tugas1_iris_2006464266.repository;
import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DosenDb extends JpaRepository<DosenModel, String> {
    // JPA
    Optional<DosenModel> findByNip(String nip);

}