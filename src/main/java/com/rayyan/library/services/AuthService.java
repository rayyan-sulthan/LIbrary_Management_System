package com.rayyan.library.services;

import com.rayyan.library.entity.Role;
import com.rayyan.library.entity.User;
import com.rayyan.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password, String role) {

        Role userRole;

        try {
            // Accept ADMIN or USER from request
            userRole = Role.valueOf("ROLE_" + role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role. Allowed values: ADMIN or USER");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(userRole)
                .build();

        return userRepository.save(user);
    }
}