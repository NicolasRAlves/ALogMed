//package com.alogmed.clinica.controller;
//
//
//import com.alogmed.clinica.dto.AppointmentRequestDTO;
//import com.alogmed.clinica.dto.AppointmentResponseDTO;
//import com.alogmed.clinica.entity.AppointmentStatus;
//import com.alogmed.clinica.entity.User;
//import com.alogmed.clinica.service.AppointmentService;
//import com.alogmed.clinica.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private UserService userService;
//    private final AppointmentService appointmentService;
//
//    public AppointmentController(AppointmentService appointmentService) {
//        this.appointmentService = appointmentService;
//    }
//
//    @PostMapping
//    public AppointmentResponseDTO schedule(@RequestBody AppointmentRequestDTO dto) {
//        return appointmentService.schedule(dto);
//    }
//
//    @PatchMapping("/{id}/cancel")
//    public void cancel(@PathVariable Long id) {
//        appointmentService.updateStatus(id, AppointmentStatus.CANCELLED);
//    }
//
//    @PatchMapping("/{id}/confirm")
//    public void confirm(@PathVariable Long id) {
//        appointmentService.updateStatus(id, AppointmentStatus.CONFIRMED);
//    }
//
//    @PatchMapping("/{id}/finish")
//    public void finish(@PathVariable Long id) {
//        appointmentService.updateStatus(id, AppointmentStatus.FINISHED);
//    }
//
//    @GetMapping("/history")
//    public List<?> history(@RequestParam Long patientId) {
//        User patient = new User(); // ideal: buscar usuário logado ou via repo
//        patient.setId(patientId);
//        return appointmentService.getHistory(patient);
//    }
//
////        @GetMapping("/my")
////        public List<?> myAppointments(@RequestParam Long patientId) {
////            User patient = new User(); // ideal: buscar usuário logado ou via repo
////            patient.setId(patientId);
////            return appointmentService.getMyAppointments(patient);
////        }
//
//    @GetMapping("/my")
//    public List<?> myAppointments(@RequestParam Long patientId) {
//        // CORREÇÃO: Buscando o usuário real pelo ID antes de passar para o serviço
//        User patient = userService.findById(patientId);
//        return appointmentService.getMyAppointments(patient);
//    }
//}
//

// Em: src/main/java/com/alogmed/clinica/controller/AppointmentController.java

package com.alogmed.clinica.controller;

import com.alogmed.clinica.dto.AppointmentRequestDTO;
import com.alogmed.clinica.dto.AppointmentResponseDTO;
import com.alogmed.clinica.entity.AppointmentStatus;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.service.AppointmentService;
import com.alogmed.clinica.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    // Injeção de dependência via construtor
    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> schedule(@RequestBody AppointmentRequestDTO dto) {
        return ResponseEntity.ok(appointmentService.schedule(dto));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        appointmentService.updateStatus(id, AppointmentStatus.CANCELLED);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long id) {
        appointmentService.updateStatus(id, AppointmentStatus.CONFIRMED);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<Void> finish(@PathVariable Long id) {
        appointmentService.updateStatus(id, AppointmentStatus.FINISHED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponseDTO>> myAppointments(@RequestParam Long patientId) {
        User patient = userService.findById(patientId);
        List<AppointmentResponseDTO> appointments = appointmentService.getMyAppointments(patient);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/history")
    public ResponseEntity<List<AppointmentResponseDTO>> history(@RequestParam Long patientId) {
        User patient = userService.findById(patientId);
        List<AppointmentResponseDTO> history = appointmentService.getHistory(patient);
        return ResponseEntity.ok(history);
    }
}