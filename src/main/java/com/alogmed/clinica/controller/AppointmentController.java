package com.alogmed.clinica.controller;


import com.alogmed.clinica.dto.AppointmentRequestDTO;
import com.alogmed.clinica.dto.AppointmentResponseDTO;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/appointments")
    public class AppointmentController {

        private final AppointmentService appointmentService;

        public AppointmentController(AppointmentService appointmentService) {
            this.appointmentService = appointmentService;
        }

        @PostMapping
        public AppointmentResponseDTO schedule(@RequestBody AppointmentRequestDTO dto) {
            return appointmentService.schedule(dto);
        }

        @PatchMapping("/{id}/cancel")
        public void cancel(@PathVariable Long id) {
            appointmentService.updateStatus(id, AppointmentStatus.CANCELLED);
        }

        @PatchMapping("/{id}/confirm")
        public void confirm(@PathVariable Long id) {
            appointmentService.updateStatus(id, AppointmentStatus.CONFIRMED);
        }

        @PatchMapping("/{id}/finish")
        public void finish(@PathVariable Long id) {
            appointmentService.updateStatus(id, AppointmentStatus.FINISHED);
        }

        @GetMapping("/history")
        public List<?> history(@RequestParam Long patientId) {
            User patient = new User(); // ideal: buscar usuário logado ou via repo
            patient.setId(patientId);
            return appointmentService.getHistory(patient);
        }

        @GetMapping("/my")
        public List<?> myAppointments(@RequestParam Long patientId) {
            User patient = new User(); // ideal: buscar usuário logado ou via repo
            patient.setId(patientId);
            return appointmentService.getMyAppointments(patient);
        }
    }

