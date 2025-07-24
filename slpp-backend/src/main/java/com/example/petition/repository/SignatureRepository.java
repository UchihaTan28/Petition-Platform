package com.example.petition.repository;

import com.example.petition.model.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
    List<Signature> findByPetitionId(Long petitionId);
    List<Signature> findByUserId(Long userId);
    // method to check duplicates:
    boolean existsByPetitionIdAndUserId(Long petitionId, Long userId);
}
