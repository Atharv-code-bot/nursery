package com.sakshi.nursery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AuthResponse {
    private String token;
    private String role;
    private Long id;

    public AuthResponse(String token, String role, Long id) {
        this.token = token;
        this.role = role;
        this.id = id;
    }


}
