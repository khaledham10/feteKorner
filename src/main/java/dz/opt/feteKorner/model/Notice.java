package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="notice_id_seq",allocationSize = 1)
@Entity
@Table(name="Notice")
public class Notice {

    @GeneratedValue(generator = "notice_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    @Column(nullable = false)
    private String comment;

    private int note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @JsonBackReference(value = "serviceNotice")
    private Service service;

}
