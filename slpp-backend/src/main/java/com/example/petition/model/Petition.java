package com.example.petition.model;

import jakarta.persistence.*;

@Entity
public class Petition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private String status;  // "open" or "closed"
    private String response;
    private int signatures;

    // The petitioner who created this petition
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator; 

    public Petition() {}

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getText() {
        return text;
    }

    public void setText(String text) { this.text = text; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) { this.response = response; }

    public int getSignatures() {
        return signatures;
    }

    public void setSignatures(int signatures) { this.signatures = signatures; }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) { this.creator = creator; }
}
