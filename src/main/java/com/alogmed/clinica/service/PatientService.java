package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.report.PatientCreateDTO;
import com.alogmed.clinica.entity.Patient;
import com.alogmed.clinica.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public Patient create(PatientCreateDTO dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setBirthDate(dto.getBirthDate());
        patient.setHeight(dto.getHeight());
        patient.setWeight(dto.getWeight());

        return repo.save(patient);
    }
}
