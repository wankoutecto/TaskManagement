package com.example.TaskManagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {
    @Value("${SECRET_KEY}")
    private String secretKey;
    private Key key;
    @PostConstruct
    public void initKey(){
        byte[] bytes= Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object>claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000 * 10))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    public List<?> extractRoles(String token){
        return (List<?>) extractClaims(token).get("roles");
    }

    public boolean isTokenValid(String token, String userLogged){
        try {
            String usernameToken = extractUsername(token);
            Date expired = extractClaims(token).getExpiration();
            return (usernameToken.equalsIgnoreCase(userLogged) && !expired.before(new Date()));
        } catch (Exception e) {
            return false;
        }
    }
}
