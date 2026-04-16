package com.example.demo.security;

import com.example.demo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // On définit une clé en dur de 64 caractères (exactement 512 bits)
    private final String jwtSecret = "MaCleSecreteSuperLongueEtSecuriseePourLeProjetSkillHubDeSelim2026";
    private final int jwtExpirationMs = 86400000; 

    public String generateToken(User user) {
        // Transformation de la String en clé utilisable par l'algorithme
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .claim("role", user.getRole() != null ? user.getRole() : "ROLE_USER") // Sécurité si le rôle est null
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}