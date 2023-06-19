package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.MahasiswaModel;
import apap.ti.tugas1_iris_2006464266.repository.MahasiswaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService {
    @Autowired
    MahasiswaDb mahasiswaDb;

    @Override
    public void addMahasiswa(MahasiswaModel mahasiswa) {
        mahasiswaDb.save(mahasiswa);
    }

    @Override
    public List<MahasiswaModel> getListMahasiswa() {
        return mahasiswaDb.findAll();
    }

    @Override
    public MahasiswaModel getMahasiswaByUuid(String uuid) {
        Optional<MahasiswaModel> mahasiswa = mahasiswaDb.findByUuid(uuid);
        if (mahasiswa.isPresent()) {
            return mahasiswa.get();
        } else return null;
    }

    @Override
    public MahasiswaModel getMahasiswaByNpm(String npm) {
        Optional<MahasiswaModel> mahasiswa = mahasiswaDb.findByNpm(npm);
        if (mahasiswa.isPresent()) {
            return mahasiswa.get();
        } else return null;
    }

    @Override
    public MahasiswaModel getMahasiswaByEmail(String email) {
        Optional<MahasiswaModel> mahasiswa = mahasiswaDb.findByEmail(email);
        if (mahasiswa.isPresent()) {
            return mahasiswa.get();
        } else return null;
    }

    @Override
    public List<MahasiswaModel> getListMahasiswaNonAktif() {
        return mahasiswaDb.findAllByStatusMahasiswa(false);
    }

    @Override
    public MahasiswaModel updateMahasiswa(MahasiswaModel mahasiswa) {
        mahasiswaDb.save(mahasiswa);
        return mahasiswa;
    }

    @Override
    public MahasiswaModel deleteMahasiswa(MahasiswaModel mahasiswa) {
        mahasiswaDb.delete(mahasiswa);
        return mahasiswa;
    }
}