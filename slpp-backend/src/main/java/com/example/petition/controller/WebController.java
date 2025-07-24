package com.example.petition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class WebController {
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        // Add any model attributes, e.g an empty user object
        // model.addAttribute("user", new User());
        return "register";  // This refers to templates/register.html
    }
}
