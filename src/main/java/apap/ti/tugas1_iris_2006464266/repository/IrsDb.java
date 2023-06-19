package apap.ti.tugas1_iris_2006464266.repository;

import apap.ti.tugas1_iris_2006464266.model.IrsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IrsDb extends JpaRepository<IrsModel, String> {
    // JPA
    Optional<IrsModel> findById(long id);



}