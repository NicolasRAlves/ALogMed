package com.alogmed.clinica.dto.report;

import java.util.List;

public record CreateMedicalRecordDTO(
        Long patientId,
        Long doctorId,
        String description,
        String diagnosis,
        List<PrescriptionDTO> prescriptions
) {
    public record PrescriptionDTO(
            String name,
            String posology,
            String duration
    ) {}
}