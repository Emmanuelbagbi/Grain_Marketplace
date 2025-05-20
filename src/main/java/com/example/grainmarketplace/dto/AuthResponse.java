package com.example.grainmarketplace.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String accessToken;
    private String refreshToken;

    // Constructor used for access & refresh token only
    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
