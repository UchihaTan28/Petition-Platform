package com.example.petition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.petition.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByBiometricId(String biometricId);
}

