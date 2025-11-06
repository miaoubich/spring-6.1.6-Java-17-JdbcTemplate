package com.miaoubich.security.controller;

import com.miaoubich.security.dto.AuthenticationRequest;
import com.miaoubich.security.dto.AuthenticationResponse;
import com.miaoubich.security.dto.RegisterRequest;
import com.miaoubich.security.service.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;

    public AuthController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/non-secure")
    public ResponseEntity<String> nonSecureEndpoint() {
        return ResponseEntity.ok("This is a non-secure endpoint accessible without authentication.");
    }
}
