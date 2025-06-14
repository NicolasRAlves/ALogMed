package com.alogmed.clinica.dto.report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientCreateDTO {
    private String name;
    private LocalDate birthDate;
    private double height;
    private double weight;
}
