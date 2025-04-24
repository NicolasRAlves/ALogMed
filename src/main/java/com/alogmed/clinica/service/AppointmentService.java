package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.AppointmentRequestDTO;
import com.alogmed.clinica.dto.AppointmentResponseDTO;
import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.repository.AppointmentRepository;
import com.alogmed.clinica.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        User patient = userRepository.findById(dto.patientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        if (appointmentRepository.existsByDoctorAndDateAndTime(doctor, dto.date(), dto.time())) {
            throw new IllegalArgumentException("Doctor is not available at this time.");
        }

        Appointment appointment = new Appointment();
        appointment.setDate(dto.date());
        appointment.setTime(dto.time());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setHospital(dto.hospital());
        appointment.setHospitalAddress(dto.hospitalAddress());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointmentRepository.save(appointment);

        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getHospital(),
                appointment.getHospitalAddress(),
                appointment.getStatus()
        );
    }

    @Transactional
    public void updateStatus(Long id, AppointmentStatus status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appt.setStatus(status);
        appointmentRepository.save(appt);
    }

    public List<Appointment> getHistory(User user) {
        return appointmentRepository.findByPatient(user); // ou por médico se necessário
    }

    public List<Appointment> getMyAppointments(User user) {
        return appointmentRepository.findByPatient(user); // ajustar lógica conforme o usuário logado
    }
}