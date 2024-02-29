package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SequenceGenerator(name = "service_data_id_seq",allocationSize = 1)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Service_data")
public class ServiceData {

    @GeneratedValue(generator = "service_data_id_seq",strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    private String url;

    private String label;

    @ManyToOne
    @JoinColumn(name="service_id")
    @JsonBackReference(value = "serviceData")
    private Service service;


}
