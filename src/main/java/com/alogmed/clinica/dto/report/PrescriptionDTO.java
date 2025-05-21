package com.alogmed.clinica.dto.report;

import java.time.LocalDate;

public class PrescriptionDTO {
    private String name;
    private String posology;
    private LocalDate startDate;
    private int duration;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}