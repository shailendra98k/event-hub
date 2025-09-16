package com.eventhub.auth.controller;

import com.eventhub.auth.dto.LoginResponse;
import com.eventhub.auth.dto.SignupRequest;
import com.eventhub.auth.dto.LoginRequest;
import com.eventhub.auth.dto.UserInfo;
import com.eventhub.auth.entity.Role;
import com.eventhub.auth.entity.User;
import com.eventhub.auth.service.UserService;
import com.eventhub.shared.dto.ErrorDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            HttpHeaders headers = new HttpHeaders();
            headers.add("jwtToken", loginResponse.getToken());
            headers.add("Set-Cookie", "jwtToken=" + loginResponse.getToken() + "; HttpOnly; Path=/; SameSite=Strict");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDetails(400, e.getMessage()));
        }
    }


    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        UserInfo userOpt = userService.getInfo();
        if (userOpt == null) {
            return ResponseEntity.status(404).body(new ErrorDetails(404, "User not found"));
        }
        return ResponseEntity.ok(userOpt);
    }

    @PatchMapping("/{userId}/role/{role}")
    public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @PathVariable Role role) {
        try {
            User updatedUser = userService.updateUserRole(userId, role);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorDetails(400, e.getMessage()));
        }
    }
}
