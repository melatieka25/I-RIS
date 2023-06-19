package apap.ti.tugas1_iris_2006464266.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dosen_mata_kuliah")
@IdClass(DosenMataKuliahId.class)
public class DosenMataKuliahModel implements Serializable {

    //Relasi dengan MataKuliahModel
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_mata_kuliah", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MataKuliahModel mataKuliah;

    //Relasi dengan DosenModel
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "nip", referencedColumnName = "nip", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DosenModel dosen;

    @NotNull
    @Size(max = 20)
    @Column(name = "ruang_kelas", nullable = false)
    private String ruangKelas;

}
