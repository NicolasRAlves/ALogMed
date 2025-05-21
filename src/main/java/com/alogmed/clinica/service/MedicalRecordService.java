package com.alogmed.clinica.service;

import com.alogmed.clinica.entity.MedicalRecord;
import com.alogmed.clinica.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository repository;

    public MedicalRecord save(MedicalRecord record) {
        return repository.save(record);
    }
}
