package com.alogmed.clinica.repository;


import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorAndDateAndTime(User doctor, LocalDate date, LocalTime time);
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);

}
