package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.DosenMataKuliahModel;
import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import apap.ti.tugas1_iris_2006464266.repository.DosenMataKuliahDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DosenMataKuliahServiceImpl implements DosenMataKuliahService {
    @Autowired
    DosenMataKuliahDb dosenMataKuliahDb;

    @Override
    public DosenMataKuliahModel deleteDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah) {
        dosenMataKuliahDb.delete(dosenMataKuliah);
        return  dosenMataKuliah;
    }

    @Override
    public void addDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah) {
        dosenMataKuliahDb.save(dosenMataKuliah);
    }

    @Override
    public List<DosenMataKuliahModel> getListDosenMataKuliah() {
        return dosenMataKuliahDb.findAll();
    }

    @Override
    public DosenMataKuliahModel getDosenMataKuliahByDosenAndMataKuliah(DosenModel dosen, MataKuliahModel mataKuliah) {
        Optional<DosenMataKuliahModel> dosenMataKuliah = dosenMataKuliahDb.findByDosenAndMataKuliah(dosen, mataKuliah);
        if (dosenMataKuliah.isPresent()) {
            return dosenMataKuliah.get();
        } else return null;
    }

    @Override
    public DosenMataKuliahModel updateDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah) {
        dosenMataKuliahDb.save(dosenMataKuliah);
        return dosenMataKuliah;
    }
}