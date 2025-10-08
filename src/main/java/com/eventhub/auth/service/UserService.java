package com.eventhub.auth.service;

import com.eventhub.auth.dto.UserInfo;
import com.eventhub.auth.entity.Role;
import com.eventhub.auth.entity.User;
import com.eventhub.auth.repository.UserRepository;
import com.eventhub.auth.dto.SignupRequest;
import com.eventhub.auth.dto.LoginRequest;
import com.eventhub.auth.util.JwtUtil;
import com.eventhub.auth.dto.LoginResponse;
import com.eventhub.shared.dto.EmailMessage;
import com.eventhub.shared.producer.EmailProducer;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private EmailProducer emailProducer;

    public User signup(SignupRequest request) throws BadRequestException {
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        User user = new User();
        user.setEmail(request.email);
        user.setHashedPassword(passwordEncoder.encode(request.password));
        user.setRole(Role.BUYER);
        user.setCreatedAt(Instant.now());
        user.setFirstName(request.firstName);
        user.setLastName(request.lastName);

        User savedUser = userRepository.save(user);
        // Send welcome email via RabbitMQ
        String name = savedUser.getFirstName() != null ? savedUser.getFirstName() : savedUser.getEmail();
        EmailMessage welcomeMsg = new EmailMessage(
            savedUser.getEmail(),
            "Welcome to EventHub!",
            "Hello " + name + ",\n\nWelcome to EventHub! We're glad to have you on board."
        );
        emailProducer.sendEmail(welcomeMsg);
        return savedUser;
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email);
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.password, user.getHashedPassword())) {
            return null;
        }
        // Send sign-in notification email via RabbitMQ
        String name = user.getFirstName() != null ? user.getFirstName() : user.getEmail();
        EmailMessage signInMsg = new EmailMessage(
            user.getEmail(),
            "Sign-In Notification",
            "Hello " + name + ",\n\nYour account was just signed in. If this wasn't you, please contact support."
        );
        emailProducer.sendEmail(signInMsg);
        return LoginResponse.builder()
                .token(jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name()))
                .firstName(user.getFirstName())
                .role(user.getRole())
                .build();

    }


    public UserInfo getInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            String email = userDetails.getUsername();
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                return UserInfo.builder().
                        id(user.get().getId()).
                        email(user.get().getEmail()).
                        firstName(user.get().getFirstName()).
                        role(user.get().getRole()).
                        build();
            }
        }
        return null;
    }

    public User updateUserRole(Long userId, Role role) throws BadRequestException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        User user = userOpt.get();
        user.setRole(role);
        return userRepository.save(user);
    }
}
