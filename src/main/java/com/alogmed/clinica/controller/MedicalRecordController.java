package com.alogmed.clinica.controller;

import com.alogmed.clinica.dto.report.CreateMedicalRecordDTO;
import com.alogmed.clinica.entity.MedicalRecord;
import com.alogmed.clinica.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> createRecord(@RequestBody CreateMedicalRecordDTO dto) {
        MedicalRecord newRecord = medicalRecordService.create(dto);
        return ResponseEntity.ok(newRecord);
    }
}