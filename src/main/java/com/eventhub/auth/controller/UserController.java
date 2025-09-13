package com.eventhub.auth.controller;

import com.eventhub.auth.dto.LoginResponse;
import com.eventhub.auth.dto.SignupRequest;
import com.eventhub.auth.dto.LoginRequest;
import com.eventhub.auth.entity.User;
import com.eventhub.auth.service.UserService;
import com.eventhub.shared.dto.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.signup(request);
            return ResponseEntity.status(201).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDetails(400, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse loginResponse = userService.login(request);
            if (loginResponse == null) {
                return ResponseEntity.status(401).body(new ErrorDetails(401, "Invalid email or password"));
            }
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDetails(400, e.getMessage()));
        }
    }
}
