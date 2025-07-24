package com.example.petition.model;

import jakarta.persistence.*;

@Entity
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long petitionId;
    private Long userId;

    public Signature() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public Long getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(Long petitionId) { this.petitionId = petitionId; }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) { this.userId = userId; }
}
