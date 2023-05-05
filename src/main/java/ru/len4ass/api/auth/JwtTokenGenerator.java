package ru.len4ass.api.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenGenerator {
    @Resource
    private JwtSecret jwtSecret;

    public String generateJwtToken(UserDetails userDetails, Date expirationDate) {
        return generateJwtToken(new HashMap<>(), userDetails, expirationDate);
    }

    public String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails, Date expirationDate) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(jwtSecret.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
