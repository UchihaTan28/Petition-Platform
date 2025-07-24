package com.example.petition.controller;

import com.example.petition.model.Petition;
import com.example.petition.service.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slpp")
@CrossOrigin(origins = "http://localhost:4200")  // Allow frontend access if needed
public class OpenDataController {

    @Autowired
    private PetitionService petitionService;

    /**
     * Get all petitions or filter by status (open/closed)
     *  
     * GET /slpp/petitions             - all petitions
     * GET /slpp/petitions?status=open - only open petitions
     */
    @GetMapping("/petitions")
    public Map<String, List<Map<String, String>>> getAllPetitions(@RequestParam(value = "status", required = false) String status) {
        List<Petition> petitions;

        // If status filter is applied
        if (status != null && (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed"))) {
            petitions = petitionService.getPetitionsByStatus(status);
        } else {
            petitions = petitionService.getAllPetitions();
        }

        // Format response as required
        List<Map<String, String>> formattedPetitions = petitions.stream().map(petition -> {
            Map<String, String> petitionMap = new LinkedHashMap<>();
            petitionMap.put("petition_id", String.valueOf(petition.getId()));
            petitionMap.put("status", petition.getStatus());
            petitionMap.put("petition_title", petition.getTitle());
            petitionMap.put("petition_text", petition.getText());
            petitionMap.put("petitioner", petition.getCreator() != null ? petition.getCreator().getEmail() : "N/A");
            petitionMap.put("signatures", String.valueOf(petition.getSignatures()));
            petitionMap.put("response", petition.getResponse() != null ? petition.getResponse() : "Parliamentary debates Note: the government will consider the\r\n"
            		+ "proposal if...");
            return petitionMap;
        }).collect(Collectors.toList());

        Map<String, List<Map<String, String>>> response = new HashMap<>();
        response.put("petitions", formattedPetitions);

        return response;
    }
}
