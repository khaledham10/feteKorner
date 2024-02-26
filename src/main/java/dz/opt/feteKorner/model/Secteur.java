package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="Secteur")
@SequenceGenerator(name = "secteur_id_seq",allocationSize = 1)
public class Secteur {
    @GeneratedValue(generator = "secteur_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;
    @Column(nullable = false)
    private String label;


    @JsonBackReference(value = "serviceSecteur")
    @OneToMany(mappedBy = "secteur",cascade = CascadeType.ALL)
    private List<Service> services;

}

