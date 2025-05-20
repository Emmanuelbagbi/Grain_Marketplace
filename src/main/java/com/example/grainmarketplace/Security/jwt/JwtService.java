package com.example.grainmarketplace.Security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalStateException("JWT secret key must be at least 32 characters long");
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates an access token for the given username
     * @param username the username to include in the token
     * @return the generated JWT token
     */
    public String generateAccessToken(String username) {
        return buildToken(username, jwtExpirationMs);
    }

    /**
     * Generates a refresh token for the given username
     * @param username the username to include in the token
     * @return the generated refresh token
     */
    public String generateRefreshToken(String username) {
        return buildToken(username, refreshExpirationMs);
    }

    /**
     * Builds a JWT token with the given username and expiration time
     */
    private String buildToken(String username, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from the JWT token
     * @param token the JWT token to parse
     * @return the username contained in the token
     * @throws JwtException if the token is invalid
     */
    public String extractUsername(String token) throws JwtException {
        return parseToken(token).getBody().getSubject();
    }

    /**
     * Validates whether a token is valid for the given username
     * @param token the JWT token to validate
     * @param username the username to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (ExpiredJwtException ex) {
            log.warn("JWT token expired: {}", ex.getMessage());
            return false;
        } catch (UnsupportedJwtException ex) {
            log.warn("Unsupported JWT token: {}", ex.getMessage());
            return false;
        } catch (MalformedJwtException ex) {
            log.warn("Malformed JWT token: {}", ex.getMessage());
            return false;
        } catch (SecurityException ex) {
            log.warn("Invalid JWT signature: {}", ex.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            log.warn("JWT claims string is empty: {}", ex.getMessage());
            return false;
        }
    }

    /**
     * Checks if a token is expired
     * @param token the token to check
     * @return true if expired, false otherwise
     * @throws JwtException if the token is invalid
     */
    private boolean isTokenExpired(String token) throws JwtException {
        return parseToken(token).getBody().getExpiration().before(new Date());
    }

    /**
     * Parses and validates a JWT token
     * @param token the token to parse
     * @return the parsed claims
     * @throws JwtException if the token is invalid
     */
    private Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
