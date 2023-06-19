package apap.ti.tugas1_iris_2006464266.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mahasiswa")
public class MahasiswaModel implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;

    @NotNull
    @Size(max = 10)
    @Column(name = "npm", nullable = false, unique = true)
    private String npm;

    @NotNull
    @Size(max = 255)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_depan", nullable = false)
    private String namaDepan;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama_belakang", nullable = false)
    private String namaBelakang;

    @NotNull
    @Column(name = "status_mahasiswa", nullable = false)
    private Boolean statusMahasiswa;

    // Relasi dengan IrsModel
    @OneToMany(mappedBy = "mahasiswa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IrsModel> listIrs;

}
