package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.report.CreateMedicalRecordDTO;
import com.alogmed.clinica.entity.*;
import com.alogmed.clinica.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository recordRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;

    public MedicalRecordService(
            MedicalRecordRepository repository,
            UserRepository userRepository,
            PatientRepository patientRepository,
            PrescriptionRepository prescriptionRepository
    ) {
        this.recordRepository = repository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Transactional
    public MedicalRecord create(CreateMedicalRecordDTO dto) {
        if (dto.patientId() == null || dto.doctorId() == null) {
            throw new IllegalArgumentException("ID do Paciente e do Doutor não podem ser nulos.");
        }

        Patient patient = patientRepository.findById(dto.patientId())
                .orElseThrow(() -> new RuntimeException("ERRO: Paciente não encontrado com o ID: " + dto.patientId()));

        User doctor = userRepository.findById(dto.doctorId())
                .orElseThrow(() -> new RuntimeException("ERRO: Doutor não encontrado com o ID: " + dto.doctorId()));

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setDescription(dto.description());
        record.setDiagnosis(dto.diagnosis());
        record.setDate(LocalDateTime.now());

        MedicalRecord savedRecord = recordRepository.save(record);

        if (dto.prescriptions() != null && !dto.prescriptions().isEmpty()) {
            List<CreateMedicalRecordDTO.PrescriptionDTO> validPrescriptions = dto.prescriptions().stream()
                    .filter(p -> p.name() != null && !p.name().trim().isEmpty())
                    .toList();

            if (!validPrescriptions.isEmpty()) {
                List<Prescription> prescriptionEntities = new ArrayList<>();
                for (CreateMedicalRecordDTO.PrescriptionDTO pDto : validPrescriptions) {
                    Prescription prescription = new Prescription();
                    prescription.setName(pDto.name());
                    prescription.setPosology(pDto.posology());
                    prescription.setDuration(pDto.duration());
                    prescription.setMedicalRecord(savedRecord);
                    prescriptionEntities.add(prescription);
                }
                List<Prescription> savedPrescriptions = prescriptionRepository.saveAll(prescriptionEntities);
                savedRecord.setPrescriptions(savedPrescriptions);
            }
        }
        return savedRecord;
    }
}