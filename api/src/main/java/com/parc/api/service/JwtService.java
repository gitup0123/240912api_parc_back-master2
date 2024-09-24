package com.parc.api.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.function.Function;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenBlacklistService tokenBlacklistService;
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${jwt.clock-skew-seconds}")
    private long clockSkewSeconds;  // Nouvelle variable pour la tolérance d'horloge
    // Génère un token JWT avec un email
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }
    // Crée un token JWT avec des claims supplémentaires et l'email de l'utilisateur
    public String createToken(Map<String, Object> claims, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);  // Durée de validité du token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)  // Définit la date d'expiration
                .signWith(getSignKey(), SignatureAlgorithm.HS256)  // Utilise la clé secrète pour signer le token
                .compact();
    }
    // Récupère la clé secrète pour signer le token
    private Key getSignKey() {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT secret key: " + e.getMessage());
        }
    }
    // Extrait la date d'expiration du token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }    // Valide le token en vérifiant l'email et si le token a expiré
    public Boolean validateToken(String token, String email) {
        final String extractedUsername = extractEmail(token);
        return (extractedUsername.equals(email) && !isTokenExpired(token));
    }
    // Vérifie si le token est expiré
    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
    // Extrait l'email de l'utilisateur à partir du token JWT
    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }
    // Extrait une revendication (claim) spécifique du token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // Extrait toutes les revendications (claims) du token JWT, y compris la gestion des erreurs
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .setAllowedClockSkewSeconds(clockSkewSeconds)  // Ajout de la tolérance d'horloge
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Gestion spécifique des jetons expirés
            throw new RuntimeException("JWT token has expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            // Gestion des autres erreurs JWT
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
    // Extrait le token JWT de la requête HTTP (Authorization header)
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Retire "Bearer " pour obtenir le token JWT
        }
        return null;
    }
    // Extrait la date d'expiration du token
    public Date extractExpirationDate(String token) {
        return extractExpiration(token);
    }
}

