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
     * Inclui informações do paciente e lista de prontuários com prescrições.
     *
     * @param patientId ID do paciente
     * @return PatientReportDTO com os dados do relatório
     * @throws RuntimeException se o paciente não for encontrado
     */
    public PatientReportDTO getPatientDetailedReport(Long patientId) {
        // Busca o paciente pelo ID
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + patientId));

        // Busca todos os prontuários do paciente
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);

        // Cria o DTO do relatório
        PatientReportDTO report = new PatientReportDTO();
        report.setName(patient.getName());
        report.setAge(calculateAge(patient.getBirthDate()));
        report.setHeight(patient.getHeight());
        report.setWeight(patient.getWeight());
        report.setRecords(mapRecords(records));

        return report;
    }

    /**
     * Mapeia uma lista de MedicalRecord para uma lista de RecordBlock.
     *
     * @param records Lista de prontuários
     * @return Lista de RecordBlock para o DTO
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
     * Mapeia uma lista de Prescription para uma lista de PrescriptionDTO.
     *
     * @param prescriptions Lista de prescrições
     * @return Lista de PrescriptionDTO para o DTO
     */
    private List<PrescriptionDTO> mapPrescriptions(List<Prescription> prescriptions) {
        return prescriptions.stream().map(prescription -> {
            PrescriptionDTO dto = new PrescriptionDTO();
            dto.setName(prescription.getName());
            dto.setPosology(prescription.getPosology());
            dto.setStartDate(prescription.getStartDate());
            dto.setDuration(prescription.getDuration());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Calcula a idade com base na data de nascimento.
     *
     * @param birthDate Data de nascimento
     * @return Idade em anos
     */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0; // Ou lançar uma exceção, dependendo do requisito
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}