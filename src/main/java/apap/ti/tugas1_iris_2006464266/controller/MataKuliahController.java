package apap.ti.tugas1_iris_2006464266.controller;

import apap.ti.tugas1_iris_2006464266.model.DosenMataKuliahModel;
import apap.ti.tugas1_iris_2006464266.model.DosenModel;
import apap.ti.tugas1_iris_2006464266.model.MataKuliahModel;
import apap.ti.tugas1_iris_2006464266.service.DosenService;
import apap.ti.tugas1_iris_2006464266.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Controller
public class MataKuliahController {
    @Qualifier("mataKuliahServiceImpl")
    @Autowired
    private MataKuliahService mataKuliahService;

    @Autowired
    private DosenService dosenService;


    @GetMapping("/mata-kuliah/create-matakuliah")
    public String addMataKuliahFormPage(Model model) {
        model.addAttribute("mataKuliah", new MataKuliahModel());
        return "form-add-matakuliah";
    }

    @PostMapping(value="/mata-kuliah/create-matakuliah", params = {"addRowDosen"})
    private String addRowDosenMultiple(
            @ModelAttribute DosenMataKuliahModel dosenMataKuliah, MataKuliahModel mataKuliah, Model model
    ) {
        if (mataKuliah.getListDosenMataKuliah() == null || mataKuliah.getListDosenMataKuliah().size()==0){
            mataKuliah.setListDosenMataKuliah(new ArrayList<>());
        }


        mataKuliah.getListDosenMataKuliah().add(dosenMataKuliah);
        List<DosenMataKuliahModel> listDosenMataKuliah = mataKuliah.getListDosenMataKuliah();
        List<DosenModel> listDosen = dosenService.getListDosen();

        model.addAttribute("mataKuliah", mataKuliah);
        model.addAttribute("listDosenMataKuliahExisting", listDosenMataKuliah);
        model.addAttribute("listDosen", listDosen);

        return "form-add-matakuliah";
    }

    @PostMapping(value="/mata-kuliah/create-matakuliah", params = {"deleteRowDosen"})
    private String deleteRowDosenMultiple(
            @ModelAttribute MataKuliahModel mataKuliah,
            @RequestParam("deleteRowDosen") Integer row,
            Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        mataKuliah.getListDosenMataKuliah().remove(rowId.intValue());
        List<DosenMataKuliahModel> listDosenMataKuliah = mataKuliah.getListDosenMataKuliah();
        List<DosenModel> listDosen = dosenService.getListDosen();

        model.addAttribute("mataKuliah", mataKuliah);
        model.addAttribute("listDosenMataKuliahExisting", listDosenMataKuliah);
        model.addAttribute("listDosen", listDosen);
        return "form-add-matakuliah";
    }

    @PostMapping(value="/mata-kuliah/create-matakuliah", params = {"save"})
    public String addMataKuliahSubmitPage(@ModelAttribute MataKuliahModel mataKuliah, Model model) {
        int sks = mataKuliah.getSks();
        Duration waktuKelas = Duration.ofMinutes(50*sks);
        LocalTime waktuSelesai = (LocalTime) waktuKelas.addTo(mataKuliah.getWaktuMulai());
        String namaMatkul = mataKuliah.getNamaMatkul().substring(0, 3).toUpperCase();
        String semester = mataKuliah.getSemester().substring(0,3).toUpperCase();
        String waktuMulaiStr = String.valueOf(mataKuliah.getWaktuMulai().getHour());
        String waktuSelesaiStr = String.valueOf(waktuSelesai.getHour());
        // Referensi: https://stackoverflow.com/questions/2626835/is-there-functionality-to-generate-a-random-character-in-java
        Random r = new Random();
        char hurufSatu = (char)(r.nextInt(26) + 'A');
        char hurufDua = (char)(r.nextInt(26) + 'A');
        String code = "MK" + namaMatkul + semester + (waktuMulaiStr.length() == 1 ? "0" + waktuMulaiStr : waktuMulaiStr) + (waktuSelesaiStr.length() == 1 ? "0" + waktuSelesaiStr : waktuSelesaiStr) + hurufSatu + hurufDua;

        //Membuat objek MahasiswaModel
        while (mataKuliahService.getMataKuliahByCode(code) != null){
            hurufSatu = (char)(r.nextInt(26) + 'A');
            hurufDua = (char)(r.nextInt(26) + 'A');
            code = "MK" + namaMatkul + semester + waktuMulaiStr + waktuSelesaiStr + hurufSatu + hurufDua;
        }

        if(mataKuliah.getListDosenMataKuliah() == null){
            mataKuliah.setListDosenMataKuliah(new ArrayList<>());
        }

        mataKuliah.setCode(code);
        mataKuliah.setWaktuSelesai(waktuSelesai);

        List<DosenMataKuliahModel> listDosenMataKuliah = mataKuliah.getListDosenMataKuliah();
        List<DosenModel> listDosen = dosenService.getListDosen();

        Map<String, String> daftarNipRuangKelas = new HashMap<>();
        for (DosenMataKuliahModel dosenMataKuliah : listDosenMataKuliah){
            daftarNipRuangKelas.put(dosenMataKuliah.getDosen().getNip(), dosenMataKuliah.getRuangKelas());
        }

        List<DosenMataKuliahModel> listDosenMataKuliahBaru = new ArrayList<>();
        for(DosenModel dosen: listDosen){
            if (daftarNipRuangKelas.containsKey(dosen.getNip())){
                DosenMataKuliahModel dosenMataKuliahBaru = new DosenMataKuliahModel(mataKuliah, dosen, daftarNipRuangKelas.get(dosen.getNip()));
                listDosenMataKuliahBaru.add(dosenMataKuliahBaru);
                if(dosen.getListDosenMataKuliah() == null){
                    dosen.setListDosenMataKuliah(new ArrayList<>());
                }
                dosen.getListDosenMataKuliah().add(dosenMataKuliahBaru);
            }
        }
        mataKuliah.setListDosenMataKuliah(listDosenMataKuliahBaru);

        mataKuliahService.addMataKuliah(mataKuliah);
        model.addAttribute("code", mataKuliah.getCode());
        return "add-matakuliah";
    }


    @GetMapping("/mata-kuliah")
    public String listMataKuliah(Model model) {
        List<MataKuliahModel> listMataKuliah = mataKuliahService.getListMataKuliah();
        model.addAttribute("listMataKuliah", listMataKuliah);
        return "list-matakuliah";
    }

    @GetMapping("/mata-kuliah/{id}")
    public String viewDetailMataKuliahPage(@PathVariable(value = "id") long id, Model model) {
        MataKuliahModel mataKuliah = mataKuliahService.getMataKuliahById(id);
        if (mataKuliah != null) {
            model.addAttribute("mataKuliah", mataKuliah);
            model.addAttribute("listDosenMataKuliah", mataKuliah.getListDosenMataKuliah());
            return "detail-matakuliah";
        } else {
            model.addAttribute("errorMessage", "Mata kuliah dengan id" + id + " tidak ditemukan. Gagal melihat mata kuliah");
            return "error-page";
        }

    }

    @GetMapping("/filter/mata-kuliah")
    public String filterMataKuliahSubmit(@RequestParam(value = "dosen") String nip, @RequestParam(value = "semester") String semester, Model model) {
        if(nip.equals("null")){
            List<DosenModel> listDosen = dosenService.getListDosen();
            model.addAttribute("listDosen", listDosen);

            return "filter-mata-kuliah";
        }
        DosenModel dosen = dosenService.getDosenByNIP(nip);
        //List<MataKuliahModel> listMataKuliahSemester = mataKuliahService.getListMataKuliahBySemesterQuery(semester);
        List<DosenMataKuliahModel> listDosenMataKuliah = dosen.getListDosenMataKuliah();
        List<MataKuliahModel> listMataKuliah = new ArrayList<>();


        for (int i = 0; i < listDosenMataKuliah.size(); i++){
            MataKuliahModel mataKuliah = listDosenMataKuliah.get(i).getMataKuliah();
            if (mataKuliah.getSemester().toLowerCase().contains(semester.toLowerCase())){
                listMataKuliah.add(mataKuliah);
            }
        }

        List<DosenModel> listDosen = dosenService.getListDosen();
        model.addAttribute("listDosen", listDosen);
        model.addAttribute("listMataKuliah", listMataKuliah);
        model.addAttribute("nip", nip);
        model.addAttribute("semester", semester);
        return "filter-result";
    }

    @GetMapping("/mata-kuliah/{code}/update")
    public String updateMataKuliahFormPage(@PathVariable String code, Model model){

        MataKuliahModel mataKuliah = mataKuliahService.getMataKuliahByCode(code);
        if (mataKuliah != null){
            model.addAttribute("mataKuliah", mataKuliah);

            return "form-update-matakuliah";
        } else {
            model.addAttribute("errorMessage", "Mata kuliah dengan code " + code + " tidak ditemukan. Gagal mengupdate mata kuliah");
            return "error-page";
        }
    }

    @PostMapping("/mata-kuliah/update")
    public String updateMataKuliahSubmitPage(@ModelAttribute MataKuliahModel mataKuliah, Model model) {
        MataKuliahModel updatedMataKuliah = mataKuliahService.updateMataKuliah(mataKuliah);
        model.addAttribute("code", updatedMataKuliah.getCode());

        return "update-matakuliah";
    }

    @GetMapping("/mata-kuliah/{id}/delete")
    public String deleteMataKuliahFormPage(@PathVariable long id, Model model){

        MataKuliahModel mataKuliah = mataKuliahService.getMataKuliahById(id);
        if (mataKuliah != null && mataKuliah.getListIrs().size() == 0){
            MataKuliahModel deletedMataKuliah = mataKuliahService.deleteMataKuliah(mataKuliah);
            model.addAttribute("code", deletedMataKuliah.getCode());

            return "delete-matakuliah";
        } else {
            if (mataKuliah == null){
                model.addAttribute("errorMessage", "Mata Kuliah dengan id " + id + " tidak ditemukan. Gagal menghapus mata kuliah");
                return "error-page";
            }
            model.addAttribute("errorMessage", "Mata Kuliah dengan id " + id + " memiliki IRS. Gagal menghapus mata kuliah");
            return "error-page";
        }
    }
}
