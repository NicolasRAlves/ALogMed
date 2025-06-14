package com.alogmed.clinica.controller;

import com.alogmed.clinica.dto.UserDTO;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            User authenticatedUser = userService.authenticateUser(email, password);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso");
            response.put("user", UserDTO.fromEntity(authenticatedUser)); // Resposta correta com o objeto User!


            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }
}