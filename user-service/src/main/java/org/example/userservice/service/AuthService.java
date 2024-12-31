package org.example.userservice.service;

import org.example.userservice.model.Role;
import org.example.userservice.model.User;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(User user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.valueOf("ROLE_USER")));
        repository.save(user);
        return jwtService.generateToken(user.getUsername());
    }

    public String login(User user) {
        User existingUser = repository.findByUsername(user.getUsername());
        if (existingUser == null || !passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return jwtService.generateToken(existingUser.getUsername());
    }
}
