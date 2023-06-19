package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;

import java.util.List;

public interface MataKuliahService {
    MataKuliahModel deleteMataKuliah(MataKuliahModel mataKuliah);

    void addMataKuliah(MataKuliahModel mataKuliah);
    List<MataKuliahModel> getListMataKuliah();
    MataKuliahModel getMataKuliahByCode(String code);

    MataKuliahModel updateMataKuliah(MataKuliahModel mataKuliah);

    MataKuliahModel getMataKuliahById(long id);

}