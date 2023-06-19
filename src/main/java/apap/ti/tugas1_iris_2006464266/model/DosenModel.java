package apap.ti.tugas1_iris_2006464266.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dosen")
public class DosenModel {
    @Id
    @Size(max = 10)
    @Column(name = "nip")
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_depan", nullable = false)
    private String namaDepan;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_belakang", nullable = false)
    private String namaBelakang;

    // Relasi dengan DosenMataKuliahModel
    @OneToMany(mappedBy = "dosen", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DosenMataKuliahModel> listDosenMataKuliah;

    public String getNamaLengkap(){
        return namaDepan + " " + namaBelakang;
    }

}
