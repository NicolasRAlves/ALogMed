package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.report.PatientReportDTO;
import com.alogmed.clinica.dto.report.PrescriptionDTO;
import com.alogmed.clinica.entity.MedicalRecord;
import com.alogmed.clinica.entity.Patient;
import com.alogmed.clinica.entity.Prescription;
import com.alogmed.clinica.repository.MedicalRecordRepository;
import com.alogmed.clinica.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public ReportService(PatientRepository patientRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public PatientReportDTO getPatientDetailedReport(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);

        PatientReportDTO report = new PatientReportDTO();
        report.setName(patient.getName());
        report.setAge(calculateAge(patient.getBirthDate()));
        report.setHeight(patient.getHeight());
        report.setWeight(patient.getWeight());
        report.setRecords(mapRecords(records));

        return report;
    }

    private List<PatientReportDTO.RecordBlock> mapRecords(List<MedicalRecord> records) {
        return records.stream().map(record -> {
            PatientReportDTO.RecordBlock block = new PatientReportDTO.RecordBlock();
            block.setDescription(record.getDescription());
            block.setDiagnosis(record.getDiagnosis());
            block.setDate(record.getDate()); // Linha corrigida

            if (record.getPrescriptions() != null) {
                block.setPrescriptions(mapPrescriptions(record.getPrescriptions()));
            }

            return block;
        }).collect(Collectors.toList());
    }

    private List<PrescriptionDTO> mapPrescriptions(List<Prescription> prescriptions) {
        return prescriptions.stream().map(p -> {
            PrescriptionDTO dto = new PrescriptionDTO();
            dto.setName(p.getName());
            dto.setPosology(p.getPosology());
            dto.setStartDate(p.getStartDate());
            dto.setDuration(p.getDuration());
            return dto;
        }).collect(Collectors.toList());
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}