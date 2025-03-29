package com.alogmed.clinica.repository;

import com.alogmed.clinica.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByCpf(String cpf);
}

