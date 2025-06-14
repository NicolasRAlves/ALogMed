package com.alogmed.clinica.entity;

import jakarta.persistence.*;
import java.time.LocalDate; // Importe o LocalDate
import java.util.Objects;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String posology;
    private String duration;

    // --- CAMPO ADICIONADO ---
    private LocalDate startDate; // Adicionando o campo que estava faltando

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    // Construtores
    public Prescription() {
    }

    public Prescription(Long id, String name, String posology, String duration, LocalDate startDate, MedicalRecord medicalRecord) {
        this.id = id;
        this.name = name;
        this.posology = posology;
        this.duration = duration;
        this.startDate = startDate;
        this.medicalRecord = medicalRecord;
    }

    // --- Getters e Setters Manuais ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    // --- GETTER E SETTER ADICIONADOS PARA O NOVO CAMPO ---
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}