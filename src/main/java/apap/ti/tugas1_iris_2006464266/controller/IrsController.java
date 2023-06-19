package apap.ti.tugas1_iris_2006464266.controller;

import apap.ti.tugas1_iris_2006464266.model.IrsModel;
import apap.ti.tugas1_iris_2006464266.model.MahasiswaModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import apap.ti.tugas1_iris_2006464266.service.IrsService;
import apap.ti.tugas1_iris_2006464266.service.MahasiswaService;
import apap.ti.tugas1_iris_2006464266.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class IrsController {
    @Qualifier("irsServiceImpl")
    @Autowired
    private IrsService irsService;
    @Autowired
    private MahasiswaService mahasiswaService;

    @Autowired
    private MataKuliahService mataKuliahService;

    @GetMapping("/mahasiswa/{npm}/create-irs")
    public String addIrsFormPage(@PathVariable(value = "npm") String npm, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        List<MataKuliahModel> listMatkulExisting = mataKuliahService.getListMataKuliah();
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("listMataKuliah", listMatkulExisting);
        IrsModel irs = new IrsModel();
        List<MataKuliahModel> listMatkul = new ArrayList<>();
        irs.setListMataKuliah(listMatkul);
        for (MataKuliahModel matkulEx : listMatkulExisting){
            listMatkul.add(new MataKuliahModel());
        }
        model.addAttribute("irs", irs);
        return "form-add-irs";
    }

    @PostMapping("/mahasiswa/{npm}/create-irs")
    public String addIrsSubmitPage(@PathVariable(value = "npm") String npm, @ModelAttribute IrsModel irs, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        String statusDefault = "Belum Disetujui";
        String semesterStr = irs.getSemesterStr().substring(0,3).toUpperCase();
        String tahunAwalStr = String.valueOf(irs.getTahunAwal()).substring(2,4);
        String tahunAkhirStr = String.valueOf(irs.getTahunAkhir()).substring(2,4);
        String semesterCode = semesterStr + tahunAwalStr + "/" + tahunAkhirStr;

        irs.setMahasiswa(mahasiswa);
        irs.setStatus(statusDefault);
        irs.setSemester(semesterCode);

        for (IrsModel irsLain : mahasiswa.getListIrs()){
            if (irsLain.getSemester() == irs.getSemester()){
                model.addAttribute("errorMessage", "Gagal menambah IRS,  IRS semester  " + irs.getSemester() + " telah dibuat sebelumnya");
                return "error-page";
            }
        }

        List<MataKuliahModel> listMataKuliahBaru = new ArrayList<>();
        for (int i = 0; i < irs.getListMataKuliah().size(); i++) {
            MataKuliahModel mataKuliah = irs.getListMataKuliah().get(i);
            if (mataKuliah.getId() == null) {
                continue;
            } else {
                MataKuliahModel matkulAsli = mataKuliahService.getMataKuliahById(mataKuliah.getId());
                listMataKuliahBaru.add(matkulAsli);
            }
        }
        irs.setListMataKuliah(listMataKuliahBaru);

        if (irs.getTotalSks() > 24){
            model.addAttribute("errorMessage", "Gagal menambah IRS,  SKS lebih dari 24");
            return "error-page";
        }

        for (MataKuliahModel mataKuliah : irs.getListMataKuliah()){

            if (mataKuliah.isSudahPenuh()){
                model.addAttribute("errorMessage", "Gagal menambah IRS, terdapat Mata Kuliah yang sudah penuh");
                return "error-page";
            }
            if (!mataKuliah.getSemester().equals(irs.getSemesterStr())){
                model.addAttribute("errorMessage", "Gagal menambah IRS, IRS semester " + irs.getSemester() + " tidak bisa menambahkan mata kuliah " + mataKuliah.getNamaMatkul() + " semester " + mataKuliah.getSemester());
                return "error-page";
            }
        }

        Set<MataKuliahModel> mataKuliahSet = new HashSet<MataKuliahModel>(irs.getListMataKuliah());
        if (mataKuliahSet.size() < irs.getListMataKuliah().size()){
            model.addAttribute("errorMessage", "Gagal menambah IRS, terdapat Mata Kuliah yang sama diambil lebih dari 1 kali");
            return "error-page";
        }

        for (MataKuliahModel mataKuliah : irs.getListMataKuliah()){
            if (mataKuliah.getListIrs() == null){
                mataKuliah.setListIrs(new ArrayList<IrsModel>());
            }
            mataKuliah.getListIrs().add(irs);
        }

        mahasiswa.getListIrs().add(irs);
        irsService.addIrs(irs);
        model.addAttribute("semester", irs.getSemester());
        model.addAttribute("namaMahasiswa", irs.getMahasiswa().getNamaDepan()+ " " + irs.getMahasiswa().getNamaBelakang());
        return "add-irs";
    }

    @GetMapping("/mahasiswa/{npm}/{id-irs}")
    public String viewDetailIrsPage(@PathVariable(value = "npm") String npm, @PathVariable(value = "id-irs") long idIrs, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        IrsModel irs = irsService.getIrsById(idIrs);
        if (irs != null) {
            List<MataKuliahModel> listMatkul = irs.getListMataKuliah();
            Collections.sort(listMatkul);
            model.addAttribute("irs", irs);
            model.addAttribute("mahasiswa", mahasiswa);
            return "detail-irs";
            
        } else {
            model.addAttribute("errorMessage", "IRS dengan id " + idIrs + " tidak ditemukan. Gagal melihat IRS");
            return "error-page";
        }

    }

    @GetMapping("/mahasiswa/{npm}/{id-irs}/update")
    public String updateIrsFormPage(@PathVariable (value = "npm") String npm, @PathVariable (value = "id-irs") long idIrs, Model model){

        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        IrsModel irs = irsService.getIrsById(idIrs);
        List<MataKuliahModel> listMatkulExisting = mataKuliahService.getListMataKuliah();
        List<MataKuliahModel> listMatkulBaru = new ArrayList<>();
        for (MataKuliahModel mataKuliah : listMatkulExisting){
            if (irs.getListMataKuliah().contains(mataKuliah)){
                listMatkulBaru.add(mataKuliah);
            } else {
                listMatkulBaru.add(null);
            }
        }
        irs.setListMataKuliah(listMatkulBaru);

    
        model.addAttribute("irs", irs);
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("listMataKuliah", listMatkulExisting);

        return "form-update-irs";
        
    }

    @PostMapping("/mahasiswa/{npm}/{id-irs}/update")
    public String updateIrsSubmitPage(@PathVariable (value = "npm") String npm, @ModelAttribute IrsModel irs, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        List<MataKuliahModel> listMataKuliahBaru = new ArrayList<>();
        IrsModel irsLama = irsService.getIrsById(irs.getId());
        for (int i = 0; i < irs.getListMataKuliah().size(); i++) {
            MataKuliahModel mataKuliah = irs.getListMataKuliah().get(i);
            if (mataKuliah.getId() == null) {
                continue;
            } else {
                MataKuliahModel matkulAsli = mataKuliahService.getMataKuliahById(mataKuliah.getId());
                listMataKuliahBaru.add(matkulAsli);
            }
        }
        irs.setListMataKuliah(listMataKuliahBaru);

        if (irs.getTotalSks() > 24){
            model.addAttribute("errorMessage", "Gagal menambah IRS,  SKS lebih dari 24");
            return "error-page";
        }

        for (MataKuliahModel mataKuliah : irs.getListMataKuliah()){

            if (mataKuliah.isSudahPenuh()){
                model.addAttribute("errorMessage", "Gagal menambah IRS, terdapat Mata Kuliah yang sudah penuh");
                return "error-page";
            }
            if (!mataKuliah.getSemester().equalsIgnoreCase(irsLama.getSemesterStr())){
                model.addAttribute("errorMessage", "Gagal menambah IRS, IRS semester " + irsLama.getSemester() + " tidak bisa menambahkan mata kuliah " + mataKuliah.getNamaMatkul() + " semester " + mataKuliah.getSemester());
                return "error-page";
            }
        }

        Set<MataKuliahModel> mataKuliahSet = new HashSet<MataKuliahModel>(irs.getListMataKuliah());
        if (mataKuliahSet.size() < irs.getListMataKuliah().size()){
            model.addAttribute("errorMessage", "Gagal menambah IRS, terdapat Mata Kuliah yang sama diambil lebih dari 1 kali");
            return "error-page";
        }

        for (MataKuliahModel mataKuliah : irsLama.getListMataKuliah()){
            if(!irs.getListMataKuliah().contains(mataKuliah)){
                mataKuliah.getListIrs().remove(irsLama);
            }
        }

        irsLama.getListMataKuliah().clear();
        irsLama.setListMataKuliah(listMataKuliahBaru);
        irsLama.setStatus(irs.getStatus());

        for (MataKuliahModel mataKuliah : irsLama.getListMataKuliah()){
            if (mataKuliah.getListIrs() == null){
                mataKuliah.setListIrs(new ArrayList<IrsModel>());
            }
            if(!mataKuliah.getListIrs().contains(irsLama)){
                mataKuliah.getListIrs().add(irsLama);
            }
        }

        irsService.updateIrs(irsLama);
        model.addAttribute("semester", irsLama.getSemester());
        return "update-irs";
    }
}