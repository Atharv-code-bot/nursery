package com.sakshi.nursery.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    Key getkey = generateRandomKey(); // This generates a random key used for signing and verifying JWT tokens


    // This method generates a random secret key used for signing the JWT token
    @SneakyThrows
    public static Key generateRandomKey()  {
        // Using KeyGenerator to generate a random 256-bit key for HS256 (HMAC with SHA-256)
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256, new SecureRandom()); // Initializes the key generator with 256-bit key and a secure random source
        SecretKey secretKey = keyGenerator.generateKey(); // Generates the secret key
        return secretKey; // Return the generated secret key
    }

    public String extractUsername(String token) {
        try {
            return extractAllClaims(token).getSubject();
        } catch (Exception e) {
            return null; // Return null if the token is invalid
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractAllClaims(token).getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getkey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration == null || expiration.before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50)) // 50 min validity
                .signWith(getkey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token); // Only check if token is expired
        } catch (Exception e) {
            return false;
        }
    }
}
