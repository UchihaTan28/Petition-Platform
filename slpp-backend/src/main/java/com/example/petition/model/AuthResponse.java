package com.example.petition.model;

public class AuthResponse {
    private String token;
    private Long id;
    private String fullName;
    private String role;

    public AuthResponse(String token, Long id, String fullName, String role) {
        this.token = token;
        this.id = id;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
