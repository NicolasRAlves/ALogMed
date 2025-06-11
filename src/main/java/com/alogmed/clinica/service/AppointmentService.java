//package com.alogmed.clinica.service;
//
//import com.alogmed.clinica.dto.AppointmentRequestDTO;
//import com.alogmed.clinica.dto.AppointmentResponseDTO;
//import com.alogmed.clinica.entity.Appointment;
//import com.alogmed.clinica.entity.AppointmentStatus;
//import com.alogmed.clinica.entity.User;
//import com.alogmed.clinica.repository.AppointmentRepository;
//import com.alogmed.clinica.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AppointmentService {
//
//    private final AppointmentRepository appointmentRepository;
//    private final UserRepository userRepository;
//
//    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
//        this.appointmentRepository = appointmentRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public AppointmentResponseDTO schedule(AppointmentRequestDTO dto) {
//        User doctor = userRepository.findById(dto.doctorId())
//                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
//        User patient = userRepository.findById(dto.patientId())
//                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
//
//        if (appointmentRepository.existsByDoctorAndDateAndTime(doctor, dto.date(), dto.time())) {
//            throw new IllegalArgumentException("Doctor is not available at this time.");
//        }
//
//        Appointment appointment = new Appointment();
//        appointment.setDate(dto.date());
//        appointment.setTime(dto.time());
//        appointment.setDoctor(doctor);
//        appointment.setPatient(patient);
//        appointment.setHospital(dto.hospital());
//        appointment.setType(dto.type());
//        appointment.setSpecialty(dto.specialty());
//        appointment.setHospitalAddress(dto.hospitalAddress());
//        appointment.setStatus(AppointmentStatus.SCHEDULED);
//
//        appointmentRepository.save(appointment);
//
//        return new AppointmentResponseDTO(
//                appointment.getId(),
//                appointment.getDate(),
//                appointment.getTime(),
//                appointment.getHospital(),
//                appointment.getHospitalAddress(),
//                appointment.getStatus(),
//                appointment.getType(),
//                appointment.getSpecialty()
//        );
//    }
//
//    @Transactional
//    public void updateStatus(Long id, AppointmentStatus status) {
//        Appointment appt = appointmentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
//        appt.setStatus(status);
//        appointmentRepository.save(appt);
//    }
//
//    public List<Appointment> getHistory(User user) {
//        return appointmentRepository.findByPatient(user); // ou por médico se necessário
//    }
//
//    public List<Appointment> getMyAppointments(User user) {
//        return appointmentRepository.findByPatient(user); // ajustar lógica conforme o usuário logado
//    }
//
//
//}

// Em: src/main/java/com/alogmed/clinica/service/AppointmentService.java

package com.alogmed.clinica.service;


import com.alogmed.clinica.dto.AppointmentRequestDTO;
import com.alogmed.clinica.dto.AppointmentResponseDTO;
import com.alogmed.clinica.entity.Appointment;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.repository.AppointmentRepository;
import com.alogmed.clinica.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Use o Transactional do Spring

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    // Injeção de dependência via construtor (boa prática)
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional // Lembre-se de usar o import: org.springframework.transaction.annotation.Transactional
    public AppointmentResponseDTO schedule(AppointmentRequestDTO dto) {
        // 1. Busca as entidades do médico e paciente
        User doctor = userRepository.findById(dto.doctorId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
        User patient = userRepository.findById(dto.patientId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        // 2. Valida a disponibilidade
        if (appointmentRepository.existsByDoctorAndDateAndTime(doctor, dto.date(), dto.time())) {
            throw new IllegalArgumentException("Médico não está disponível neste horário.");
        }

        // 3. Cria e preenche a nova entidade de agendamento
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

        // 4. SALVA E CAPTURA A ENTIDADE PERSISTIDA (AGORA COM ID!)
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 5. (Opcional) Adicione um log para ter certeza de que o ID foi gerado
        System.out.println(">>> Agendamento salvo com SUCESSO no banco. ID gerado: " + savedAppointment.getId());

        // 6. USA O MÉTODO DE FÁBRICA PARA CRIAR UM DTO COMPLETO E CORRETO
        // Ele vai pegar os nomes do médico/paciente e o ID do 'savedAppointment'
        return AppointmentResponseDTO.fromEntity(savedAppointment);
    }

    @Transactional
    public void updateStatus(Long id, AppointmentStatus status) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado"));
        appt.setStatus(status);
        appointmentRepository.save(appt);
    }

    // Busca os agendamentos futuros de um paciente
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

    // Busca o histórico (agendamentos passados) de um paciente
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
}