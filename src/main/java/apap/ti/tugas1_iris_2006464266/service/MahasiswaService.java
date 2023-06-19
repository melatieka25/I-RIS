package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.MahasiswaModel;

import java.util.List;

public interface MahasiswaService {

    MahasiswaModel deleteMahasiswa(MahasiswaModel mahasiswa);

    void addMahasiswa(MahasiswaModel mahasiswa);
    List<MahasiswaModel> getListMahasiswa();
    MahasiswaModel getMahasiswaByUuid(String uuid);
    MahasiswaModel getMahasiswaByNpm(String npm);
    MahasiswaModel updateMahasiswa(MahasiswaModel mahasiswa);

    MahasiswaModel getMahasiswaByEmail(String email);

    List<MahasiswaModel> getListMahasiswaNonAktif();

    //List<MahasiswaModel> getListOrderedMahasiswa();
}