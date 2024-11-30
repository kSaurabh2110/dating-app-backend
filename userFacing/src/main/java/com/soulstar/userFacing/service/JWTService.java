package com.soulstar.userFacing.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    @Value("${com.soulstar.userFacing.service.JWTService.secretKey}")
    private String secretKey;

    public String generateToken(String phoneNumber) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); // Use Keys.hmacShaKeyFor to generate a key from your secret
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key, SignatureAlgorithm.HS256) // Updated signing method
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Create the signing key
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            // Parse and validate the token
            Jwts.parserBuilder()
                    .setSigningKey(key) // Set the signing key
                    .build()
                    .parseClaimsJws(token); // Parse the token and validate the signature

            return true; // If no exceptions, the token is valid
        } catch (Exception e) {
            return false; // If there's an exception, the token is invalid
        }
    }
}
