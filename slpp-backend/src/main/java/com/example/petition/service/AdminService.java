package com.example.petition.service;

import com.example.petition.model.Petition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Petitions Committee (Admin) functionalities:
 * - Setting signature threshold
 * - Evaluating petitions for closure
 */
@Service
public class AdminService {

    @Autowired
    private PetitionService petitionService;

    // Example of a signature threshold, default=1000
    private int signatureThreshold = 1000;

    public void setSignatureThreshold(int threshold) {
        this.signatureThreshold = threshold;
    }

    public int getSignatureThreshold() {
        return this.signatureThreshold;
    }

    /**
     * Evaluate and close the petition if it meets the threshold.
     * If petition is null or below threshold, returns null.
     */
    /*public Petition evaluateAndClosePetition(Long petitionId) {
        Petition petition = petitionService.getPetitionById(petitionId);

        if (petition.getSignatures() >= signatureThreshold && !"closed".equalsIgnoreCase(petition.getStatus())) {
            return petitionService.closePetition(petitionId, "Reached required signature threshold.");
        }

        throw new RuntimeException("Petition has not met the required signature threshold.");
    }*/
    public boolean evaluateAndClosePetition(Long petitionId) {
        Petition petition = petitionService.getPetitionById(petitionId);

        if (petition == null) {
            throw new RuntimeException("Petition not found with ID: " + petitionId);
        }

        System.out.println("Evaluating petition ID: " + petitionId);
        System.out.println("Signatures: " + petition.getSignatures());
        System.out.println("Threshold: " + signatureThreshold);

        // Only return true if the threshold is met, don't close yet
        return petition.getSignatures() >= signatureThreshold;
    }
    
}
