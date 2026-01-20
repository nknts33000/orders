package com.example.order_project.controllers;

import com.example.order_project.dto.request.AuthRequestDTO;
import com.example.order_project.dto.response.AuthResponseDTO;
import com.example.order_project.service.user.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthRequestDTO authRequestDTO){
        authService.registerUser(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = authService.login(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        authService.logout(token);

        return ResponseEntity.noContent().build();
    }
}
