package com.jsr_dev.medical_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jsr_dev.medical_api.domain.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private String issuer;
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.issuer = "jsr_dev";
    }

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating the JWT", exception);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }

    public String extractSubject(String jwtToken) {
        return decodeToken(jwtToken).getSubject();
    }

    private DecodedJWT decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new IllegalArgumentException("Invalid or Expired Token");
        }
    }

    public boolean isValid(String jwtToken, UserDetails foundUser) {
        DecodedJWT decodedJWT = decodeToken(jwtToken);
        String email = extractSubject(jwtToken);

        return foundUser.getUsername().equals(email) && !isExpiredToken(decodedJWT);
    }

    private boolean isExpiredToken(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date(System.currentTimeMillis()));
    }
}
