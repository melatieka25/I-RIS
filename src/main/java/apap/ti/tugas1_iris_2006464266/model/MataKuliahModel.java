package apap.ti.tugas1_iris_2006464266.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mata_kuliah")
public class MataKuliahModel implements Serializable, Comparable<MataKuliahModel>{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 14)
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Size(max = 6)
    @Column(name = "semester", nullable = false)
    private String semester;

    @NotNull
    @Column(name = "kapasitas_kelas", nullable = false)
    private Integer kapasitasKelas;

    @NotNull
    @Column(name = "sks", nullable = false)
    private Integer sks;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_matkul", nullable = false)
    private String namaMatkul;

    @NotNull
    @Size(max = 20)
    @Column(name = "hari", nullable = false)
    private String hari;

    @NotNull
    @Column(name = "waktu_mulai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuMulai;

    @NotNull
    @Column(name = "waktu_selesai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuSelesai;

    // Relasi dengan DosenMataKuliahModel
    @OneToMany(mappedBy = "mataKuliah", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DosenMataKuliahModel> listDosenMataKuliah;

    public String getDosen(){
        String res = "";
        int i = 0;
        for (i = 0; i < listDosenMataKuliah.size()-1; i++){
            res = res + listDosenMataKuliah.get(i).getDosen().getNamaLengkap() + ", ";
        }

        res = res + listDosenMataKuliah.get(i).getDosen().getNamaLengkap();
        return res;
    }

    public String getRuang(){
        String res = "";
        int i = 0;
        for (i = 0; i < listDosenMataKuliah.size()-1; i++){
            res = res + listDosenMataKuliah.get(i).getRuangKelas() + ", ";
        }

        res = res + listDosenMataKuliah.get(i).getRuangKelas();
        return res;
    }

    // Relasi dengan IrsModel
    @ManyToMany
    @JoinTable(name = "irs_mata_kuliah", joinColumns = @JoinColumn(name = "id_irs"), inverseJoinColumns = @JoinColumn(name = "id_mata_kuliah"))
    private List<IrsModel> listIrs;

    public int getTotalMahasiswa(){
        return listIrs.size();
    }

    public boolean isSudahPenuh(){
        return listIrs.size() == kapasitasKelas;
    }

    @Override
    public int compareTo(MataKuliahModel o) {
        return this.namaMatkul.compareTo(o.namaMatkul);
    }
}
