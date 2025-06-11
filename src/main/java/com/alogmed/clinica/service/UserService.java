//package com.alogmed.clinica.service;
//
//import com.alogmed.clinica.dto.UserDTO;
//import com.alogmed.clinica.entity.User;
//import com.alogmed.clinica.exception.DuplicateUserException;
//import com.alogmed.clinica.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public UserDTO saveUser(User user) {
//        User existingByEmail = userRepository.findByEmail(user.getEmail());
//        if (existingByEmail != null) {
//            throw new DuplicateUserException("Já existe um usuário com o email:  " + user.getEmail());
//        }
//
//        User existingByCpf = userRepository.findByCpf(user.getCpf());
//        if (existingByCpf != null) {
//            throw new DuplicateUserException("Já existe um usuário com o CPF: " + user.getCpf());
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        return UserDTO.fromEntity(savedUser);
//    }
//
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public User findById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
//    }
//
//    public User findByCpf(String cpf) {
//        return userRepository.findByCpf(cpf);
//    }
//
//    public UserDTO findByEmailDTO(String email) {
//        User user = userRepository.findByEmail(email);
//        return user != null ? UserDTO.fromEntity(user) : null;
//    }
//
//    public UserDTO findByIdDTO(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
//        return UserDTO.fromEntity(user);
//    }
//
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//}


package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.UserDTO;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.exception.DuplicateUserException;
import com.alogmed.clinica.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // [LOG ADICIONADO] Inicializador do Logger para esta classe
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException("Já existe um usuário com o email: " + user.getEmail());
        }
        if (userRepository.findByCpf(user.getCpf()) != null) {
            throw new DuplicateUserException("Já existe um usuário com o CPF: " + user.getCpf());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Criando novo usuário com email: {}", user.getEmail());

        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    /**
     * Autentica um usuário, verificando email e senha. (COM LOGS)
     */
    @Transactional(readOnly = true)
    public User authenticateUser(String email, String password) {
        // [LOG ADICIONADO]
        logger.info("Iniciando tentativa de autenticação para o email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    // [LOG ADICIONADO]
                    logger.warn("FALHA NA AUTENTICAÇÃO: Usuário não encontrado com o email: {}", email);
                    return new BadCredentialsException("Email ou senha inválidos.");
                });

        // Compara a senha fornecida com a senha criptografada do banco
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // [LOG ADICIONADO]
            logger.warn("FALHA NA AUTENTICAÇÃO: Senha incorreta para o email: {}", email);
            throw new BadCredentialsException("Email ou senha inválidos.");
        }

        // [LOG ADICIONADO]
        logger.info("SUCESSO: Usuário autenticado com sucesso: {}", email);
        return user;
    }

    // --- Outros Métodos (sem alteração) ---

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public UserDTO findByIdDTO(Long id) {
        return UserDTO.fromEntity(findById(id));
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmailDTO(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::fromEntity)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }
}