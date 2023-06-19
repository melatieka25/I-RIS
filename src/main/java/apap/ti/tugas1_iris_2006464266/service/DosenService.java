package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.DosenModel;

import java.util.List;

public interface DosenService {
    DosenModel deleteDosen(DosenModel dosen);

    void addDosen(DosenModel dosen);
    List<DosenModel> getListDosen();
    DosenModel getDosenByNIP(String nip);


    DosenModel updateDosen(DosenModel dosen);

}