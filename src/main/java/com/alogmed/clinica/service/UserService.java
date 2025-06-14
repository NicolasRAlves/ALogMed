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

@Service
public class UserService {

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

    @Transactional(readOnly = true)
    public User authenticateUser(String email, String password) {
        logger.info("Iniciando tentativa de autenticação para o email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("FALHA NA AUTENTICAÇÃO: Usuário não encontrado com o email: {}", email);
                    return new BadCredentialsException("Email ou senha inválidos.");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("FALHA NA AUTENTICAÇÃO: Senha incorreta para o email: {}", email);
            throw new BadCredentialsException("Email ou senha inválidos.");
        }

        logger.info("SUCESSO: Usuário autenticado com sucesso: {}", email);
        return user;
    }

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