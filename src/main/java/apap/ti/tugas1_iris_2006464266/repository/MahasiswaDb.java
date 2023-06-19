package apap.ti.tugas1_iris_2006464266.repository;

import apap.ti.tugas1_iris_2006464266.model.MahasiswaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MahasiswaDb extends JpaRepository<MahasiswaModel, String> {
    // JPA
    Optional<MahasiswaModel> findByUuid(String uuid);

    Optional<MahasiswaModel> findByNpm(String npm);

    Optional<MahasiswaModel> findByEmail(String email);

    List<MahasiswaModel> findAllByStatusMahasiswa(boolean status);

}