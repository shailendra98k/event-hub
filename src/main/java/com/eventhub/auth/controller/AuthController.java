package com.eventhub.auth.controller;

import com.eventhub.auth.dto.AuthRequest;
import com.eventhub.auth.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody AuthRequest request) {
        // TODO: Implement signup logic
        return ResponseEntity.ok(new AuthResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // TODO: Implement login logic
        return ResponseEntity.ok(new AuthResponse());
    }
}
