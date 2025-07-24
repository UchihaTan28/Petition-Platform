package com.example.petition.controller;

import com.example.petition.model.AuthResponse;
import com.example.petition.model.User;
import com.example.petition.service.JwtUtil;
import com.example.petition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registering
     * Body requires { "email", "fullName", "dateOfBirth", "password", "biometricId" } 
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        // Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

        // Send back token in the response
        return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getFullName(), user.getRole().toString()));
    }
}
