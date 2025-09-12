package com.eventhub.auth.service;

import com.eventhub.auth.entity.Role;
import com.eventhub.auth.entity.User;
import com.eventhub.auth.repository.UserRepository;
import com.eventhub.auth.dto.SignupRequest;
import com.eventhub.auth.dto.LoginRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(SignupRequest request) throws BadRequestException {
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.email);
        user.setHashedPassword(passwordEncoder.encode(request.password));
        user.setRole(Optional.ofNullable(request.role).orElse(Role.BUYER));
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    public User login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        if (!passwordEncoder.matches(request.password, user.getHashedPassword())) {
            return null;
        }
        return user;
    }
}
