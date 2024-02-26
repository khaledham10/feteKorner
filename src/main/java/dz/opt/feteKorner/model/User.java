package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="Users")
@SequenceGenerator(name = "user_id_seq",allocationSize = 1)
public class User {

    @GeneratedValue(generator = "user_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;


    @JsonBackReference(value = "serviceUser")
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Service> services;


}
