package com.example.grainmarketplace.service;

import com.example.grainmarketplace.dto.*;
import com.example.grainmarketplace.models.User;
import com.example.grainmarketplace.repository.UserRepository;
import com.example.grainmarketplace.Security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String access = jwtService.generateAccessToken(user.getEmail());
        String refresh = jwtService.generateRefreshToken(user.getEmail());

        return new AuthResponse(access, refresh);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        String access = jwtService.generateAccessToken(request.getEmail());
        String refresh = jwtService.generateRefreshToken(request.getEmail());

        return new AuthResponse(access, refresh);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String email = jwtService.extractUsername(request.getRefreshToken());

        if (!jwtService.isTokenValid(request.getRefreshToken(), email)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccess = jwtService.generateAccessToken(email);
        String newRefresh = jwtService.generateRefreshToken(email);

        return new AuthResponse(newAccess, newRefresh);
    }
}
