package com.example.petition.service;

import com.example.petition.model.Petition;
import com.example.petition.repository.PetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetitionService {

    @Autowired
    private PetitionRepository petitionRepository;

    //Returns all petitions (open or closed).
    public List<Petition> getAllPetitions() {
        return petitionRepository.findAll();
    }
    
 // Fetch petitions by status (open/closed)
    public List<Petition> getPetitionsByStatus(String status) {
        return petitionRepository.findByStatusIgnoreCase(status);
    }

     //Finds petition by ID or returns null if not found.
    public Petition getPetitionById(Long petitionId) {
        return petitionRepository.findById(petitionId)
                .orElseThrow(() -> new RuntimeException("Petition not found with ID: " + petitionId));
    }

     //Creates a new petition with default status "open" and signatures = 0.
    public Petition createPetition(Petition petition) {
        petition.setStatus("open");
        petition.setSignatures(0);
        return petitionRepository.save(petition);
    }

     //Closes the petition by setting status="closed" and recording a response.
    public Petition closePetition(Long petitionId, String response) {
        Petition petition = getPetitionById(petitionId);
        if (petition == null) {
            throw new RuntimeException("Petition not found.");
        }
        // If petition is already closed, optionally handle that
        if ("closed".equalsIgnoreCase(petition.getStatus())) {
            throw new RuntimeException("Petition is already closed.");
        }

        // Close it
        petition.setStatus("closed");
        petition.setResponse(response);
        return petitionRepository.save(petition);
    }
}
