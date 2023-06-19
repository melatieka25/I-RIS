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
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "irs")
public class IrsModel implements Serializable{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Size(max = 8)
    @Column(name = "semester", nullable = false)
    private String semester;

    // Relasi dengan MahasiswaModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "npm_mahasiswa", referencedColumnName = "npm", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MahasiswaModel mahasiswa;

    private String semesterStr;
    private int totalSks;
    private int tahunAwal;
    private int tahunAkhir;

    public int getTotalSks(){
        if (listMataKuliah == null){
            return 0;
        }
        totalSks = 0;
        for(int i = 0; i < listMataKuliah.size(); i++){
            totalSks += listMataKuliah.get(i).getSks();
        }
        return totalSks;
    }

    @ManyToMany(mappedBy = "listIrs")
    List<MataKuliahModel> listMataKuliah;

}
