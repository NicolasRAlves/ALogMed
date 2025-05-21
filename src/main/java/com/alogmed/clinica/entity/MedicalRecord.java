package com.alogmed.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String diagnosis;
    private LocalDateTime date;

    @ManyToOne
    private Patient patient;

    @OneToMany(mappedBy = "record")
    private List<Prescription> prescriptions;
}