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

    /**
     * Gera o relatório detalhado de um paciente com base no ID.
     */
    public PatientReportDTO getPatientDetailedReport(Long patientId) {
        // 1. Buscar paciente
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        // 2. Buscar registros médicos do paciente
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);

        // 3. Montar DTO com os dados
        PatientReportDTO report = new PatientReportDTO();
        report.setName(patient.getName());
        report.setAge(calculateAge(patient.getBirthDate()));
        report.setHeight(patient.getHeight());
        report.setWeight(patient.getWeight());
        report.setRecords(mapRecords(records));

        return report;
    }

    /**
     * Converte registros médicos em blocos de relatório.
     */
    private List<PatientReportDTO.RecordBlock> mapRecords(List<MedicalRecord> records) {
        return records.stream().map(record -> {
            PatientReportDTO.RecordBlock block = new PatientReportDTO.RecordBlock();
            block.setDescription(record.getDescription());
            block.setDiagnosis(record.getDiagnosis());
            block.setDate(record.getDate());
            block.setPrescriptions(mapPrescriptions(record.getPrescriptions()));
            return block;
        }).collect(Collectors.toList());
    }

    /**
     * Converte prescrições em DTOs.
     */
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

    /**
     * Calcula a idade do paciente.
     */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
