package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired // <--- IL TE MANQUE PROBABLEMENT CE BLOC
    private JwtUtils jwtUtils;

    public User register(User user) {
        // C'est ici qu'on s'assure que le rôle est bien sauvegardé
        return userRepository.save(user);
    }

    public String login(User user) {
        // On cherche l'utilisateur en base par son email
        // (Pour le TP, on simplifie : on génère le token directement)
        return jwtUtils.generateToken(user); 
    }
}