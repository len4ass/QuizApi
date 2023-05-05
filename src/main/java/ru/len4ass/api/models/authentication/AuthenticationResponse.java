package ru.len4ass.api.models.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AuthenticationResponse {
    @JsonProperty("status")
    private AuthenticationResponseType authenticationResponseType;

    @JsonProperty("info")
    private String infoMessage;

    @JsonProperty("token")
    private String jwtToken;

    public AuthenticationResponse() {

    }

    public AuthenticationResponse(
            AuthenticationResponseType authenticationResponseType,
            String infoMessage,
            String jwtToken) {
        this.authenticationResponseType = authenticationResponseType;
        this.infoMessage = infoMessage;
        this.jwtToken = jwtToken;
    }

    public AuthenticationResponseType getAuthenticationResponseType() {
        return authenticationResponseType;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthenticationResponse that = (AuthenticationResponse) o;
        return Objects.equals(jwtToken, that.jwtToken);
    }

    @Override
    public int hashCode() {
        return jwtToken != null ? jwtToken.hashCode() : 0;
    }
}
