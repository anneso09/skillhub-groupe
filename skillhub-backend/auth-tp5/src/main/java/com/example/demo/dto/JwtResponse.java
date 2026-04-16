package com.example.demo.dto;

public class JwtResponse {
    private String token;
    private String email;
    private String role;
    private String type = "Bearer";

    public JwtResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    // Getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getType() { return type; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setType(String type) { this.type = type; }
}