package com.alogmed.clinica.dto.report;

import java.time.LocalDateTime;
import java.util.List;

public class PatientReportDTO {
    private String name;
    private int age;
    private double height;
    private double weight;
    private List<RecordBlock> records;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<RecordBlock> getRecords() {
        return records;
    }

    public void setRecords(List<RecordBlock> records) {
        this.records = records;
    }

    public static class RecordBlock {
        private String description;
        private String diagnosis;
        private LocalDateTime date;
        private List<PrescriptionDTO> prescriptions;

        // Getters e Setters
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }

        public List<PrescriptionDTO> getPrescriptions() {
            return prescriptions;
        }

        public void setPrescriptions(List<PrescriptionDTO> prescriptions) {
            this.prescriptions = prescriptions;
        }
    }
}