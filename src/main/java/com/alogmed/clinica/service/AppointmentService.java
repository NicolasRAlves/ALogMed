package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.AppointmentRequestDTO;
import com.alogmed.clinica.dto.AppointmentResponseDTO;
import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.Role;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.repository.AppointmentRepository;
import com.alogmed.clinica.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AppointmentResponseDTO schedule(AppointmentRequestDTO dto) {
        User doctor = userRepository.findById(dto.doctorId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
        User patient = userRepository.findById(dto.patientId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        if (appointmentRepository.existsByDoctorAndDateAndTime(doctor, dto.date(), dto.time())) {
            throw new IllegalArgumentException("Médico não está disponível neste horário.");
        }

        Appointment appointment = new Appointment();
        appointment.setDate(dto.date());
        appointment.setTime(dto.time());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setHospital(dto.hospital());
        appointment.setType(dto.type());
        appointment.setSpecialty(dto.specialty());
        appointment.setHospitalAddress(dto.hospitalAddress());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentResponseDTO.fromEntity(savedAppointment);
    }

    @Transactional
    public void updateStatus(Long id, AppointmentStatus status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        appt.setStatus(status);
        appointmentRepository.save(appt);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getMyAppointments(User patient) {
        List<Appointment> appointments = appointmentRepository.findByPatientAndStatusIn(
                patient,
                Arrays.asList(AppointmentStatus.SCHEDULED, AppointmentStatus.CONFIRMED)
        );
        return appointments.stream()
                .map(AppointmentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getHistory(User patient) {
        List<Appointment> appointments = appointmentRepository.findByPatientAndStatusIn(
                patient,
                Arrays.asList(AppointmentStatus.FINISHED, AppointmentStatus.CANCELLED)
        );
        return appointments.stream()
                .map(AppointmentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(User doctor) {
        if (doctor.getRole() != Role.DOCTOR) {
            throw new IllegalArgumentException("O ID fornecido não pertence a um doutor.");
        }

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);

        return appointments.stream()
                .map(AppointmentResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}