package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.IrsModel;

import java.util.List;

public interface IrsService {
    IrsModel deleteIrs(IrsModel irs);

    void addIrs(IrsModel irs);
    List<IrsModel> getListIrs();
    IrsModel getIrsById(long id);

    IrsModel updateIrs(IrsModel irs);

}