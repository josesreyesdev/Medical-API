package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.user.AuthenticationMapper;
import com.jsr_dev.medical_api.domain.user.AuthenticationRequest;
import com.jsr_dev.medical_api.domain.user.AuthenticationResponse;
import com.jsr_dev.medical_api.domain.user.User;
import com.jsr_dev.medical_api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authenticate = authenticationManager.authenticate(authToken);

        var generatedToken = tokenService.generateToken((User) authenticate.getPrincipal());

        return ResponseEntity.ok().body(AuthenticationMapper.mapToAuthResponse(generatedToken));
    }
}
