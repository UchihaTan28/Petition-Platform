package com.example.petition.controller;

import com.example.petition.model.Petition;
import com.example.petition.service.AdminService;
import com.example.petition.service.PetitionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PetitionService petitionService;

    /**
     * Sets the global signature threshold.
     * Example: POST /api/admin/setThreshold?threshold=500
     */
    @PostMapping("/setThreshold")
    public ResponseEntity<String> setSignatureThreshold(@RequestParam int threshold) {
        adminService.setSignatureThreshold(threshold);
        
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")  // Explicitly set content type
                .body("Threshold updated to " + threshold);
    }

    /**
     * Closes a petition manually by providing a custom response body.
     * Example: POST /api/admin/close?petitionId=1
     */
    @PostMapping("/close")
    public Petition closePetition(@RequestParam Long petitionId, @RequestBody String response) {
        return petitionService.closePetition(petitionId, response);
    }

    /**
     * Evaluates a petition for closure based on the current signature threshold.
     * If the petition is at/above threshold, it gets closed automatically.
     * Example: POST /api/admin/evaluate?petitionId=1
     */
 
    @PostMapping("/evaluate")
    public boolean evaluatePetition(@RequestParam Long petitionId) {
        return adminService.evaluateAndClosePetition(petitionId);
    }
    
    @GetMapping("/allPetitions")
    public List<Petition> viewAllPetitions() {
        return petitionService.getAllPetitions();
    }

    
    @GetMapping("/getThreshold")
    public int getSignatureThreshold() {
        return adminService.getSignatureThreshold();
    }
}
