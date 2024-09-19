package com.test.controller;

import com.test.security.jwt.JwtUtils;
import com.test.security.model.AuthenticationRequest;
import com.test.security.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        String token = jwtUtils.generateJwtToken(authenticationRequest);

        return new AuthenticationResponse().setToken(token);
    }

    @PostMapping("/generate-hashes")
    public List<String> generateHashes(@RequestBody List<String> passwords) {
        return passwords.stream()
                .map(passwordEncoder::encode)
                .collect(Collectors.toList());
    }
}
