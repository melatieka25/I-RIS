package apap.ti.tugas1_iris_2006464266.repository;

import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MataKuliahDb extends JpaRepository<MataKuliahModel, String> {
    // JPA
    Optional<MataKuliahModel> findByCode(String code);
    Optional<MataKuliahModel> findById(long id);

}