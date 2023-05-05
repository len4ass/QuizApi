package ru.len4ass.api.services;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.len4ass.api.auth.JwtTokenGenerator;
import ru.len4ass.api.models.authentication.AuthenticationResponse;
import ru.len4ass.api.models.authentication.AuthenticationResponseType;
import ru.len4ass.api.models.authentication.LoginRequest;
import ru.len4ass.api.models.authentication.SignupRequest;
import ru.len4ass.api.models.user.User;
import ru.len4ass.api.repositories.UserRepository;
import ru.len4ass.api.validators.PasswordValidator;
import ru.len4ass.api.validators.UsernameValidator;

import java.util.Date;

@Service
public class AuthenticationService {
    @Resource
    private final UserRepository userRepository;

    @Resource
    private final JwtTokenGenerator jwtTokenGenerator;

    @Resource
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            JwtTokenGenerator jwtTokenGenerator,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AuthenticationResponse> register(SignupRequest request) {
        var userInDb = userRepository.findByUsername(request.getUsername());
        if (userInDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(
                            AuthenticationResponseType.FAILED,
                            String.format("User %s already exists.", request.getUsername()),
                            ""));
        }

        if (!UsernameValidator.isValid(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(
                            AuthenticationResponseType.FAILED,
                            UsernameValidator.getInvalidConstraints(),
                            ""
                    ));
        }

        if (!PasswordValidator.isValid(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(
                            AuthenticationResponseType.FAILED,
                            PasswordValidator.getInvalidConstraints(),
                            ""
                    ));
        }

        var passwordEncoder = UserAuthenticationService.getPasswordEncoder();
        var user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        var jwtToken = jwtTokenGenerator.generateJwtToken(user, new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        return ResponseEntity.ok(new AuthenticationResponse(
                AuthenticationResponseType.SUCCESS,
                "Signed up successfully.",
                jwtToken));
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(
                    AuthenticationResponseType.FAILED,
                    "Username or password in invalid.",
                    ""));
        }

        UserDetails userToAuth = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtTokenGenerator.generateJwtToken(userToAuth, new Date(System.currentTimeMillis() + 1000 * 60 * 60));
        return ResponseEntity.ok(new AuthenticationResponse(
                AuthenticationResponseType.SUCCESS,
                "Signed in successfully.",
                jwtToken));
    }
}
