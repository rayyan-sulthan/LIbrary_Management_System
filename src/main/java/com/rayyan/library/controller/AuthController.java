package com.rayyan.library.controller;

import com.rayyan.library.dto.LoginRequestDTO;
import com.rayyan.library.dto.RegisterRequestDTO;
import com.rayyan.library.services.AuthService;
import com.rayyan.library.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        authService.register(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return jwtService.generateToken(request.getUsername());
    }
}