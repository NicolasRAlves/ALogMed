//package com.alogmed.clinica.repository;
//
//
//import com.alogmed.clinica.entity.Appointment;
//import com.alogmed.clinica.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//
//public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
//
//    boolean existsByDoctorAndDateAndTime(User doctor, LocalDate date, LocalTime time);
//    List<Appointment> findByPatient(User patient);
//    List<Appointment> findByDoctor(User doctor);
//
//}

// Em: src/main/java/com/alogmed/clinica/repository/AppointmentRepository.java

package com.alogmed.clinica.repository;

import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Método para checar se já existe um agendamento para um médico em uma data e hora
    boolean existsByDoctorAndDateAndTime(User doctor, LocalDate date, LocalTime time);

    // Método para buscar agendamentos de um paciente com certos status
    List<Appointment> findByPatientAndStatusIn(User patient, List<AppointmentStatus> statuses);

}