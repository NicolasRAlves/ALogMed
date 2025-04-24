package com.alogmed.clinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO (
    Long doctorId,
    Long patientId,
    LocalDate date,
    LocalTime time,
    String hospital,
    String hospitalAddress,
    String type,
    String specialty
){}
