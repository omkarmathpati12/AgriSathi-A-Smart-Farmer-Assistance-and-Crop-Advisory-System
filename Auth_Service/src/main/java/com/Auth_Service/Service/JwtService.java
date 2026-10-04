package com.Auth_Service.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationMillis;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${jwt.expiration}") long expirationMillis) {
        this.jwtEncoder = jwtEncoder;
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String email, String role) {
        Instant issuedAt = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusMillis(expirationMillis))
                .claim("role", role)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }
}