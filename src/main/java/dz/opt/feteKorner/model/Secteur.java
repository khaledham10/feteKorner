package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "secteur_id_seq",allocationSize = 1)
@Table(name="secteur")
public class Secteur {
    @GeneratedValue(generator = "secteur_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;
    @Column(nullable = false)
    private String label;

    private String emoji;

    @JsonManagedReference(value = "serviceSecteur") // Indique le côté propriétaire
    @OneToMany(mappedBy = "secteur",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Service> services;

}

