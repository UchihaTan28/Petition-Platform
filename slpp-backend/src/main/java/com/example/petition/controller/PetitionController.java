package com.example.petition.controller;

import com.example.petition.model.Petition;
import com.example.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/petitions")
@CrossOrigin(origins = "http://localhost:4200")
public class PetitionController {

    @Autowired
    private PetitionService petitionService;

    
     // GET all petitions (for listing in the Petitioner Dashboard)
    @GetMapping
    public ResponseEntity<?> getAllPetitions() {
        try {
            List<Petition> petitions = petitionService.getAllPetitions();
            return ResponseEntity.ok(petitions);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error occurred"));
        }
    }

    /**
     * Create a new petition (status automatically set to "open").
     * Body requires { "title", "text", ... }, plus optionally "creator" if you store user link.
     */
    @PostMapping
    public Petition createPetition(@RequestBody Petition petition) {
        return petitionService.createPetition(petition);
    }

    //GET specific petition by ID
    @GetMapping("/{id}")
    public Petition getPetitionById(@PathVariable Long id) {
        return petitionService.getPetitionById(id);
    }
}
