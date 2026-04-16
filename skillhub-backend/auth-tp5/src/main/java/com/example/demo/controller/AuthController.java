package com.example.demo.controller;

// Importations nécessaires
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.security.JwtUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Annotation pour indiquer que c'est un contrôleur REST
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService; // On garde seulement celui-là

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = authService.register(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // On utilise authService au lieu de userService
        User user = authService.findByEmail(loginRequest.getEmail());
        
        // Génération du token
        String jwt = jwtUtils.generateToken(user);

        // Réponse JSON structurée
        return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getRole()));
    }
}