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
@SequenceGenerator(name = "service_id_seq",allocationSize = 1)
@Table(name="service")
public class Service {
    @GeneratedValue(generator = "service_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;
    @Column(nullable = false)
    private String label;

    private int likes;

    private int dislikes;



    @JsonManagedReference(value = "serviceData")
    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ServiceData> serviceData ;

    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference(value = "serviceNotice")
    private List<Notice> notices ;

    @JsonBackReference(value = "serviceUser")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference(value = "serviceSecteur")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "secteur_id")
    private Secteur secteur;





}
