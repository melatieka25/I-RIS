package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.DosenMataKuliahModel;
import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;

import java.util.List;

public interface DosenMataKuliahService {
    DosenMataKuliahModel deleteDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah);

    void addDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah);
    List<DosenMataKuliahModel> getListDosenMataKuliah();
    DosenMataKuliahModel getDosenMataKuliahByDosenAndMataKuliah(DosenModel dosen, MataKuliahModel mataKuliah);


    DosenMataKuliahModel updateDosenMataKuliah(DosenMataKuliahModel dosenMataKuliah);

}