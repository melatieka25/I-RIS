package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import apap.ti.tugas1_iris_2006464266.repository.DosenDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DosenServiceImpl implements DosenService {
    @Autowired
    DosenDb dosenDb;

    @Override
    public DosenModel deleteDosen(DosenModel dosen) {
        dosenDb.delete(dosen);
        return dosen;
    }

    @Override
    public void addDosen(DosenModel dosen) {
        dosenDb.save(dosen);
    }

    @Override
    public List<DosenModel> getListDosen() {
        return dosenDb.findAll();
    }

    @Override
    public DosenModel getDosenByNIP(String nip) {
        Optional<DosenModel> dosen = dosenDb.findByNip(nip);
        if (dosen.isPresent()) {
            return dosen.get();
        } else return null;
    }

    @Override
    public DosenModel updateDosen(DosenModel dosen) {
        dosenDb.save(dosen);
        return dosen;
    }
}