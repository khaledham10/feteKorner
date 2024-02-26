package dz.opt.feteKorner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Table(name="Notice")
@Entity
@SequenceGenerator(name="notice_id_seq",allocationSize = 1)
public class Notice {
    @GeneratedValue(generator = "notice_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    private Integer id;

    @Column(nullable = false)
    private String comment;

    private int note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @JsonManagedReference(value = "serviceNotice")
    private Service service;

}
