package com.example.petition.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Required fields for Petitioners
    @Column(unique = true, nullable = false)
    private String email;            // Login id
    private String fullName;         
    private LocalDate dateOfBirth;
    private String password;         // Will be stored as a hash
    private String biometricId;      // 10-digit code assigned to citizens

    // Role-based access: "PETITIONER" or "ADMIN"
    //private String role;  // e.g., "PETITIONER", "ADMIN"
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)  // Making sure the role can't be NULL
    private Role role;

    public User() {}

    public User(String email, String fullName, LocalDate dateOfBirth, String password, String biometricId, Role role) {
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.biometricId = biometricId;
        this.role = role;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getBiometricId() {
        return biometricId;
    }

    public void setBiometricId(String biometricId) { this.biometricId = biometricId; }

    public Role getRole() {
        return (Role) role;
    }

    public void setRole(Role role) { this.role = role; }
}
