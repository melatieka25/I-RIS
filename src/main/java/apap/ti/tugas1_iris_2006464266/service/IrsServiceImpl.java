package apap.ti.tugas1_iris_2006464266.service;

import apap.ti.tugas1_iris_2006464266.model.IrsModel;
import apap.ti.tugas1_iris_2006464266.repository.IrsDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IrsServiceImpl implements IrsService {
    @Autowired
    IrsDb irsDb;

    @Override
    public IrsModel deleteIrs(IrsModel irs) {
        irsDb.delete(irs);
        return irs;
    }

    @Override
    public void addIrs(IrsModel irs) {
        irsDb.save(irs);
    }

    @Override
    public List<IrsModel> getListIrs() {
        return irsDb.findAll();
    }

    @Override
    public IrsModel getIrsById(long id) {
        Optional<IrsModel> irs = irsDb.findById(id);
        if (irs.isPresent()) {
            return irs.get();
        } else return null;
    }

    @Override
    public IrsModel updateIrs(IrsModel irs) {
        return irsDb.save(irs);
    }
}