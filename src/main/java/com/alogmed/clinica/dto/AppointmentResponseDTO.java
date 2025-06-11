//package com.alogmed.clinica.dto;
//
//import com.alogmed.clinica.entity.AppointmentStatus;
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//public record AppointmentResponseDTO (
//        Long id,
//        LocalDate date,
//        LocalTime time,
//        String hospital,
//        String hospitalAddress,
//        AppointmentStatus status,
//        String type,
//        String specialty
//){}


// Em: src/main/java/com/alogmed/clinica/dto/AppointmentResponseDTO.java

package com.alogmed.clinica.dto;

import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long id,
        String doctorName,
        String patientName,
        LocalDate date,
        LocalTime time,
        String hospital,
        String hospitalAddress,
        AppointmentStatus status,
        String type,
        String specialty
) {
    // Método estático para converter a Entidade para este DTO
    public static AppointmentResponseDTO fromEntity(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDoctor() != null ? appointment.getDoctor().getName() : "Médico não definido",
                appointment.getPatient() != null ? appointment.getPatient().getName() : "Paciente não definido",
                appointment.getDate(),
                appointment.getTime(),
                appointment.getHospital(),
                appointment.getHospitalAddress(),
                appointment.getStatus(),
                appointment.getType(),      // CORRIGIDO: Agora pega a String diretamente
                appointment.getSpecialty()  // CORRIGIDO: Agora pega a String diretamente
        );
    }
}