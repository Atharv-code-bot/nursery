package com.sakshi.nursery.dto;

import java.util.UUID;

public class AuthResponse {
    private String token;
    private String role;
    private UUID id;

    public AuthResponse(String token, String role, UUID id) {
        this.token = token;
        this.role = role;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
