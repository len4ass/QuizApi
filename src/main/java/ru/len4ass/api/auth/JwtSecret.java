package ru.len4ass.api.auth;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@ConfigurationProperties(prefix = "jwtsecret")
public class JwtSecret {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(token);
        return Keys.hmacShaKeyFor(bytes);
    }
}
