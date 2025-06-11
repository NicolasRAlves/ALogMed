package com.alogmed.clinica.controller;

import com.alogmed.clinica.dto.report.PatientCreateDTO;
import com.alogmed.clinica.entity.Patient;
import com.alogmed.clinica.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody PatientCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }
}
