package com.example.auth_tp3.controller;

import com.example.auth_tp3.entity.User;
import com.example.auth_tp3.repository.UserRepository;
import com.example.auth_tp3.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // POST /api/auth/register
    @PostMapping("/auth/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody RegisterRequest request) throws Exception {

        User user = authService.register(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Inscription réussie");
        response.put("email", user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/auth/login
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest request) throws Exception {

        String token = authService.login(
                request.getEmail(),
                request.getPassword()
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }
}