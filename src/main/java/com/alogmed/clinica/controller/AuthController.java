//package com.alogmed.clinica.controller;
//
//import com.alogmed.clinica.entity.User;
//import com.alogmed.clinica.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
//        String email = loginData.get("email");
//        String password = loginData.get("password");
//
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            return ResponseEntity.status(401).body("Usuário não encontrado");
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            return ResponseEntity.status(401).body("Senha incorreta");
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Login realizado com sucesso");
//        response.put("user", user.getName());
//        // response.put("token", tokenGerado); ← isso entra depois com JWT
//
//        return ResponseEntity.ok(response);
//    }
//}
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

    // Injeção de dependência via construtor
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            // 1. Delega TODA a lógica de autenticação para o serviço
            User authenticatedUser = userService.authenticateUser(email, password);

            // 2. Monta a resposta de SUCESSO com o DTO do usuário
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso");
            response.put("user", UserDTO.fromEntity(authenticatedUser)); // Resposta correta com o objeto User!

            // No futuro, adicione o token JWT aqui. Ex:
            // String token = jwtTokenProvider.createToken(authenticatedUser.getEmail(), authenticatedUser.getRole().name());
            // response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 3. Em caso de qualquer erro de autenticação, retorna 401 com a mensagem do erro
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }
}