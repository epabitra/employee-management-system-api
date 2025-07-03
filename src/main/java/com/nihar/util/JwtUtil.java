package com.nihar.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    // ✅ Secret key for signing the JWT, injected from application.properties
    @Value("${jwt.secret}")
    private String secret;

    // ✅ Expiration time in seconds, injected from application.properties
    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * 🔐 Generates a signing key using the configured secret.
     * This key is used to sign and verify JWT tokens using HMAC SHA256.
     */
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * ✅ Generates a JWT token for the given user.
     * 
     * @param userDetails - UserDetails containing username and authorities (roles)
     * @return signed JWT token with subject (username), roles, issue and expiry time
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // 👤 set the username as subject
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority) // 🛡️ extract each role (authority)
                        .collect(Collectors.toList())) // 📋 convert to list and add as custom claim
                .setIssuedAt(new Date()) // 🕒 current time
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // ⏰ expiration time
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // ✍️ sign with key and HS256 algo
                .compact(); // 🔐 generate final JWT
    }

    /**
     * 📌 Extracts the username (subject) from the JWT token.
     * 
     * @param token - JWT token
     * @return subject (username/email)
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * 🔎 Extracts roles from the JWT token claims.
     * 
     * @param token - JWT token
     * @return List of role strings (like ROLE_ADMIN, ROLE_USER)
     */
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    /**
     * 🔐 Validates token against a user: checks username and expiration.
     * 
     * @param token - JWT token
     * @param userDetails - authenticated user details
     * @return true if token is valid
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    /**
     * ⏳ Checks if the JWT token is expired or not.
     * 
     * @param token - JWT token
     * @return true if expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 📅 Extracts expiration date from token.
     * 
     * @param token - JWT token
     * @return Date object of expiration
     */
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    /**
     * 📂 Extracts all claims (payload data) from the JWT token.
     * This is a private helper method used internally.
     * 
     * @param token - JWT token
     * @return Claims object with all token data (subject, roles, exp, etc.)
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // 🔑 set the signing key
                .build()
                .parseClaimsJws(token) // ✅ parse and verify the JWT
                .getBody(); // 📦 get payload (claims)
    }
}
