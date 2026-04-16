package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Appelle le service pour sauvegarder l'utilisateur avec son rôle
        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // Le service va vérifier l'utilisateur et retourner le Token
        String token = authService.login(user);
        
        // On retourne le token dans un petit format JSON
        return ResponseEntity.ok(java.util.Map.of("token", token));
    }
}