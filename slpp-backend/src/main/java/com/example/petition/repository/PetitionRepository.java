package com.example.petition.repository;

import com.example.petition.model.Petition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetitionRepository extends JpaRepository<Petition, Long> {
    //List<Petition> findByStatus(String status);
    List<Petition> findByStatusIgnoreCase(String status);
}
