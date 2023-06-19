package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import apap.ti.tugas1_iris_2006464266.repository.MataKuliahDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MataKuliahServiceImpl implements MataKuliahService {
    @Autowired
    MataKuliahDb mataKuliahDb;

    @Override
    public void addMataKuliah(MataKuliahModel course) {
        mataKuliahDb.save(course);
    }

    @Override
    public List<MataKuliahModel> getListMataKuliah() {
        return mataKuliahDb.findAll();
    }

    @Override
    public MataKuliahModel getMataKuliahByCode(String code) {
        Optional<MataKuliahModel> mataKuliah = mataKuliahDb.findByCode(code);
        if (mataKuliah.isPresent()) {
            return mataKuliah.get();
        } else return null;
    }

    @Override
    public MataKuliahModel updateMataKuliah(MataKuliahModel mataKuliah) {
        mataKuliahDb.save(mataKuliah);
        return mataKuliah;
    }

    @Override
    public MataKuliahModel getMataKuliahById(long id) {
        Optional<MataKuliahModel> mataKuliah = mataKuliahDb.findById(id);
        if (mataKuliah.isPresent()) {
            return mataKuliah.get();
        } else return null;
    }


    @Override
    public MataKuliahModel deleteMataKuliah(MataKuliahModel mataKuliah) {
        mataKuliahDb.delete(mataKuliah);
        return mataKuliah;
    }
}