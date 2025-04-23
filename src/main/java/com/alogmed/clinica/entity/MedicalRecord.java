//package com.alogmed.clinica.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//public class MedicalRecord {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private User patient;
//
//    @ManyToOne
//    private User doctor;
//
//    @OneToOne
////    private Appointment appointment;
//
//    private String description;
//    private String diagnosis;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//}
