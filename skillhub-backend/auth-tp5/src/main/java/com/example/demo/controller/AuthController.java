package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.security.JwtUtils;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        // Appelle le service pour sauvegarder l'utilisateur avec son rôle
        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginData) {
        
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Authentification
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Génération du Token
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        // Préparation de la réponse pour React
        Map<String, Object> body = new HashMap<>();
        body.put("token", jwt);
        body.put("email", email);
        body.put("role", "apprenant"); // Forcé pour débloquer le front-end

        return ResponseEntity.ok(body);
    }

}

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody User user) {
    //     // Le service va vérifier l'utilisateur et retourner le Token
    //     String token = authService.login(user);
        
    //     // On retourne le token dans un petit format JSON
    //     return ResponseEntity.ok(java.util.Map.of("token", token));
    // }
