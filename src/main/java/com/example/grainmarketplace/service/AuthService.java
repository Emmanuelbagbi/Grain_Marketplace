package com.example.grainmarketplace.service;



import com.example.grainmarketplace.dto.*;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    AuthResponse refreshToken(RefreshTokenRequest request);
}
