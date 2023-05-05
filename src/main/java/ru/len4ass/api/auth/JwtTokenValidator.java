package ru.len4ass.api.auth;

import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenValidator {
    @Resource
    private final JwtClaimExtractor jwtClaimExtractor;

    public JwtTokenValidator(JwtClaimExtractor jwtClaimExtractor) {
        this.jwtClaimExtractor = jwtClaimExtractor;
    }

    public boolean isTokenExpired(String jwtToken) {
        return jwtClaimExtractor.extractExpirationDate(jwtToken).before(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(String jwtToken, UserDetails userDetails) {
        String username = jwtClaimExtractor.extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }
}
