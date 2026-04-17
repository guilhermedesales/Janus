package com.guilherme.Janus.Application.Service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenClaimsService {

    public UUID getAuthUserId(Authentication authentication) {
        String subject = authentication.getName();
        return UUID.fromString(subject);
    }

    public String getEmail(Authentication authentication) {
        return readClaim(authentication, "email");
    }

    public String getNome(Authentication authentication) {
        return readClaim(authentication, "nome");
    }

    private String readClaim(Authentication authentication, String claimName) {
        Object details = authentication.getDetails();
        if (details instanceof Claims claims) {
            Object value = claims.get(claimName);
            if (value != null) {
                return value.toString();
            }
        }
        throw new IllegalStateException("JWT sem claim obrigatoria: " + claimName);
    }
}

