package com.alogmed.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String posology;
    private LocalDate startDate;
    private int duration;
    @ManyToOne
    private MedicalRecord record;
}