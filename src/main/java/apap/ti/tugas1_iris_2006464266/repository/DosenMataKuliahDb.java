package apap.ti.tugas1_iris_2006464266.repository;

import apap.ti.tugas1_iris_2006464266.model.DosenMataKuliahModel;
import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DosenMataKuliahDb extends JpaRepository<DosenMataKuliahModel, String> {
    // JPA
    Optional<DosenMataKuliahModel> findByDosenAndMataKuliah(DosenModel dosen, MataKuliahModel mataKuliah);

}