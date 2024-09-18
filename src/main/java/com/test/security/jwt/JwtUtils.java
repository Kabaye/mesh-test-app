package com.test.security.jwt;

import com.test.security.model.AuthenticationRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String CLAIM_USER_ID = "USER_ID";

    private final long jwtExpirationInMs;
    private final SecretKey key;

    public JwtUtils(@Value("${jwt.expiration-in-ms}") long jwtExpirationInMs, @Value("${jwt.key}") String jwtKey) {
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }

    public String generateJwtToken(AuthenticationRequest request) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + jwtExpirationInMs)))
                .claim(CLAIM_USER_ID, request.getEmail())
                .signWith(key)
                .compact();
    }

    public String getUsernameClaimFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(CLAIM_USER_ID, String.class);
    }

    public boolean validateJwtToken(String token) {
        Jwts.parser()
                .verifyWith(key)
                .build()
                .parse(token);
        return true;
    }
}
