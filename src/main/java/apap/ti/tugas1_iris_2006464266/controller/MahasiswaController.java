package apap.ti.tugas1_iris_2006464266.controller;

import apap.ti.tugas1_iris_2006464266.model.AllMahasiswaModel;
import apap.ti.tugas1_iris_2006464266.model.IrsModel;
import apap.ti.tugas1_iris_2006464266.model.MahasiswaModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import apap.ti.tugas1_iris_2006464266.service.IrsService;
import apap.ti.tugas1_iris_2006464266.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MahasiswaController {
    @Qualifier("mahasiswaServiceImpl")
    @Autowired
    private MahasiswaService mahasiswaService;
    @Autowired
    private IrsService irsService;




    @GetMapping("/mahasiswa/create-mahasiswa")
    public String addMahasiswaFormPage(Model model) {
        model.addAttribute("mahasiswa", new MahasiswaModel());
        return "form-add-mahasiswa";
    }

    @PostMapping("/mahasiswa/create-mahasiswa")
    public String addMahasiswaSubmitPage(@ModelAttribute MahasiswaModel mahasiswa, Model model) {

        MahasiswaModel sameNpm = mahasiswaService.getMahasiswaByNpm(mahasiswa.getNpm());
        MahasiswaModel sameEmail = mahasiswaService.getMahasiswaByEmail(mahasiswa.getEmail());

        //Membuat objek MahasiswaModel
        if (sameNpm == null && sameEmail == null){
            mahasiswaService.addMahasiswa(mahasiswa);
            model.addAttribute("npm", mahasiswa.getNpm());
            return "add-mahasiswa";
        } else if (sameNpm != null) {
            model.addAttribute("errorMessage", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan NPM lain!");
            return "error-page";
        } else {
            model.addAttribute("errorMessage", "Mahasiswa dengan email " + mahasiswa.getEmail() + " sudah pernah ditambahkan sebelumnya. Coba lagi dengan email lain!");
            return "error-page";
        }

    }


    @GetMapping("/mahasiswa")
    public String listMahasiswa(Model model) {
        List<MahasiswaModel> listMahasiswa = mahasiswaService.getListMahasiswa();
        model.addAttribute("listMahasiswa", listMahasiswa);
        return "list-mahasiswa";
    }

    @GetMapping("/mahasiswa/{npm}")
    public String viewDetailMahasiswaPage(@PathVariable(value = "npm") String npm, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        if (mahasiswa != null) {
            List<IrsModel> listIrs = mahasiswa.getListIrs();
            model.addAttribute("listIrs", listIrs);
            model.addAttribute("mahasiswa", mahasiswa);

            return "detail-mahasiswa";
        } else {
            model.addAttribute("errorMessage", "Mahasiswa dengan npm " + npm + " tidak ditemukan. Gagal melihat mahasiswa");
            return "error-page";
        }

    }

    @GetMapping("/mahasiswa/{npm}/update")
    public String updateMahasiswaFormPage(@PathVariable String npm, Model model){

        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        if (mahasiswa != null){
            model.addAttribute("mahasiswa", mahasiswa);

            return "form-update-mahasiswa";
        } else {
            model.addAttribute("errorMessage", "Mahasiswa dengan npm " + npm + " tidak ditemukan. Gagal mengupdate mahasiswa");
            return "error-page";
        }
    }

    @PostMapping("/mahasiswa/update")
    public String updateMahasiswaSubmitPage(@ModelAttribute MahasiswaModel mahasiswa, Model model) {
        MahasiswaModel updatedMahasiswa = mahasiswaService.updateMahasiswa(mahasiswa);
        model.addAttribute("npm", updatedMahasiswa.getNpm());

        return "update-mahasiswa";
    }


    @GetMapping("/mahasiswa/delete")
    public String deleteMahasiswa(Model model) {
        List<MahasiswaModel> listMahasiswaExisting = mahasiswaService.getListMahasiswaNonAktif();
        model.addAttribute("listMahasiswaEx", listMahasiswaExisting);
        model.addAttribute("allMahasiswa", new AllMahasiswaModel());
        return "form-delete-mahasiswa";
    }

    @PostMapping("/mahasiswa/delete")
    public String deleteMahasiswaSubmit(@ModelAttribute AllMahasiswaModel allMahasiswa, Model model) {
        for (String npm : allMahasiswa.getListNpmMahasiswaDelete()){
            MahasiswaModel mahasiswaAsli = mahasiswaService.getMahasiswaByNpm(npm);
            if (mahasiswaAsli == null){
                continue;
            }
            if (mahasiswaAsli.getListIrs() != null){
                for (IrsModel irs : mahasiswaAsli.getListIrs()){
                    for (MataKuliahModel mataKuliah : irs.getListMataKuliah()){
                        mataKuliah.getListIrs().remove(irs);
                    }
                    irsService.deleteIrs(irs);
                }
                mahasiswaAsli.getListIrs().clear();
            }
            mahasiswaService.deleteMahasiswa(mahasiswaAsli);
        }
        model.addAttribute("listMahasiswa", allMahasiswa.getListNpmMahasiswaDelete());
        return "delete-mahasiswa";
    }
}