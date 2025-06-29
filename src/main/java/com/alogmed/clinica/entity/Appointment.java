//package com.alogmed.clinica.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//@Getter
//@Setter
//
//@Entity
//public class Appointment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDate date;
//    private LocalTime time;
//    private String hospital;
//    private String hospitalAddress;
//    private String type;
//    private String specialty;
//
//    @Enumerated(EnumType.STRING)
//    private AppointmentStatus status;
//
//    @ManyToOne
//    @JoinColumn(name = "doctor_id")
//    private User doctor;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public LocalTime getTime() {
//        return time;
//    }
//
//    public void setTime(LocalTime time) {
//        this.time = time;
//    }
//
//    public String getHospital() {
//        return hospital;
//    }
//
//    public void setHospital(String hospital) {
//        this.hospital = hospital;
//    }
//
//    public String getHospitalAddress() {
//        return hospitalAddress;
//    }
//
//    public void setHospitalAddress(String hospitalAddress) {
//        this.hospitalAddress = hospitalAddress;
//    }
//
//    public AppointmentStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(AppointmentStatus status) {
//        this.status = status;
//    }
//
//    public User getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(User doctor) {
//        this.doctor = doctor;
//    }
//
//    public User getPatient() {
//        return patient;
//    }
//
//    public void setPatient(User patient) {
//        this.patient = patient;
//    }
//
//    public String getSpecialty() {
//        return specialty;
//    }
//
//    public void setSpecialty(String specialty) {
//        this.specialty = specialty;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "patient_id")
//    private User patient;
//
//
//
//}


// Em: src/main/java/com/alogmed/clinica/entity/Appointment.java

package com.alogmed.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "appointments") // Boa prática definir o nome da tabela
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    private LocalTime time;
    private String hospital;
    private String hospitalAddress;

    // Seus campos type e specialty estão como String na entidade, o que está correto.
    private String type;
    private String specialty;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY é uma boa prática para performance
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
}