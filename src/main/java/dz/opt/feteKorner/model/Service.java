package dz.opt.feteKorner.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="Service")
@SequenceGenerator(name = "service_id_seq",allocationSize = 1)
public class Service {
    @GeneratedValue(generator = "service_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;
    @Column(nullable = false)
    private String label;

    private int likes;

    private int dislikes;

    @Column(columnDefinition = "TEXT")
    private String descpription ;

    @JsonManagedReference(value = "serviceData")
    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    private List<ServiceData> serviceData ;

    @JsonBackReference(value = "serviceNotice")
    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    private List<Notice> notices ;

    @JsonManagedReference(value = "serviceUser")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference(value = "serviceSecteur")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "secteur_id")
    private Secteur secteur;





}
