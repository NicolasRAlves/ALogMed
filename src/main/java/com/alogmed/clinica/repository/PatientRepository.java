package com.alogmed.clinica.repository;

import com.alogmed.clinica.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}