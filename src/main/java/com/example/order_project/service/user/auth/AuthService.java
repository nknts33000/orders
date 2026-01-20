package com.example.order_project.service.user.auth;

import com.example.order_project.dto.response.AuthResponseDTO;
import com.example.order_project.enums.UserRole;
import com.example.order_project.models.User;
import com.example.order_project.repositories.UserRepository;
import com.example.order_project.security.jwt.JwtUtil;
import com.example.order_project.service.user.auth.tokenBlacklist.TokenBlacklistService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            TokenBlacklistService tokenBlacklistService
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public void registerUser(String username, String password){
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(
                new User(username, encodedPassword, UserRole.USER)
        );
    }

    public AuthResponseDTO login(@NotBlank String username, @NotBlank String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponseDTO(token);
    }

    public void logout(String token) {
        Instant expiresAt = jwtUtil.extractExpiration(token);
        tokenBlacklistService.blacklist(token, expiresAt);
    }
}
