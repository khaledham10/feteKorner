package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="Service_data")
@SequenceGenerator(name = "service_data_id_seq",allocationSize = 1)
public class ServiceData {
    @GeneratedValue(generator = "service_data_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private String url;

    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="service_id")
    @JsonBackReference(value = "serviceData")
    private Service service;


}
