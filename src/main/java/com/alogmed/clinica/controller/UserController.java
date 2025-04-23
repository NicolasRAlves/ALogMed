package com.alogmed.clinica.controller;

import com.alogmed.clinica.dto.UserDTO;
import com.alogmed.clinica.entity.Role;
import com.alogmed.clinica.entity.Status;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        UserDTO savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findByIdDTO(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.findByEmailDTO(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // editar
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.findById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setCpf(updatedUser.getCpf());
        existingUser.setRg(updatedUser.getRg());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setWeight(updatedUser.getWeight());
        existingUser.setHeight(updatedUser.getHeight());
        existingUser.setState(updatedUser.getState());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setCrm(updatedUser.getCrm());
        existingUser.setSpecialty(updatedUser.getSpecialty());
        existingUser.setSpecialty(updatedUser.getSex());

        UserDTO savedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    // funcionários
    @GetMapping("/employees")
    public ResponseEntity<List<UserDTO>> getAllEmployees() {
        List<UserDTO> employees = userService.findAll().stream()
                .filter(user -> !user.getRole().equals(Role.PATIENT))
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    // pacientes
    @GetMapping("/patients")
    public ResponseEntity<List<UserDTO>> getAllPatients() {
        List<UserDTO> patients = userService.findAll().stream()
                .filter(user -> user.getRole().equals(Role.PATIENT))
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patients);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserDTO> deactivateUser(@PathVariable Long id) {
        User user = userService.findById(id);
        user.setStatus(Status.INACTIVE);
        UserDTO deactivatedUser = userService.saveUser(user);
        return ResponseEntity.ok(deactivatedUser);
    }
}