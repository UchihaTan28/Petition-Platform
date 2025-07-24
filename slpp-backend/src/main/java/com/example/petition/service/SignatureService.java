package com.example.petition.service;

import com.example.petition.exception.CustomException;
import com.example.petition.exception.CustomException.ErrorType;
import com.example.petition.model.Petition;
import com.example.petition.model.Signature;
import com.example.petition.repository.PetitionRepository;
import com.example.petition.repository.SignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignatureService {

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private PetitionRepository petitionRepository;

    /**
     * Adds a new signature to a petition. Enforces:
     * - user can only sign once
     * - petition is "open"
     * - optionally check if user is the petition creator and block if needed
     */
    public Signature addSignature(Signature signature) {

        // Check if user already signed
        boolean alreadySigned = signatureRepository.existsByPetitionIdAndUserId(
                signature.getPetitionId(), 
                signature.getUserId()
        );
        if (alreadySigned) {
        	throw new CustomException("You have already signed this petition!", ErrorType.DUPLICATE_SIGNATURE);        }

        // Check the petition's status
        Petition petition = petitionRepository.findById(signature.getPetitionId())
                .orElseThrow(() -> new CustomException("Petition not found.", ErrorType.PETITION_NOT_FOUND));
        if (!"open".equalsIgnoreCase(petition.getStatus())) {
            throw new CustomException("Cannot sign a closed petition.", ErrorType.PETITION_CLOSED);
        }

        // Save signature
        Signature newSignature = signatureRepository.save(signature);

        // Increment signature count on petition
        petition.setSignatures(petition.getSignatures() + 1);
        petitionRepository.save(petition);

        return newSignature;
    }
}
