package com.alogmed.clinica.service;

import com.alogmed.clinica.dto.UserDTO;
import com.alogmed.clinica.entity.User;
import com.alogmed.clinica.exception.DuplicateUserException;
import com.alogmed.clinica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO saveUser(User user) {
        User existingByEmail = userRepository.findByEmail(user.getEmail());
        if (existingByEmail != null) {
            throw new DuplicateUserException("Já existe um usuário com o email:  " + user.getEmail());
        }

        User existingByCpf = userRepository.findByCpf(user.getCpf());
        if (existingByCpf != null) {
            throw new DuplicateUserException("Já existe um usuário com o CPF: " + user.getCpf());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public UserDTO findByEmailDTO(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? UserDTO.fromEntity(user) : null;
    }

    public UserDTO findByIdDTO(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        return UserDTO.fromEntity(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}