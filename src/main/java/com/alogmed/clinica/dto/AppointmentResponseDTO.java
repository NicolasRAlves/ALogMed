package com.alogmed.clinica.dto;

import com.alogmed.clinica.entity.AppointmentStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO (
        Long id,
        LocalDate date,
        LocalTime time,
        String hospital,
        String hospitalAddress,
        AppointmentStatus status
){}
