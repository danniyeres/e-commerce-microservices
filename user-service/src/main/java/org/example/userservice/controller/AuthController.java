package org.example.userservice.controller;

import org.example.userservice.model.User;
import org.example.userservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        return authService.login(user);
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable Long id) {
        return authService.getUserById(id);
    }

    @GetMapping("/getByUsername/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return authService.getUserByUsername(username);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return authService.getAllUsers();
    }
}