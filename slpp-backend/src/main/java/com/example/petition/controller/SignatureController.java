package com.example.petition.controller;

import com.example.petition.model.Signature;
import com.example.petition.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signatures")
@CrossOrigin(origins = "http://localhost:4200")
public class SignatureController {

    @Autowired
    private SignatureService signatureService;

     // This will fail if user already signed or if petition is closed, etc.
    @PostMapping
    public Signature addSignature(@RequestBody Signature signature) {
        return signatureService.addSignature(signature);
    }
}
