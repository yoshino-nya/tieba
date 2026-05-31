package org.example.tieba.controller;

import jakarta.validation.Valid;
import org.example.tieba.common.Result;
import org.example.tieba.dto.AuthResponse;
import org.example.tieba.dto.LoginRequest;
import org.example.tieba.dto.RegisterRequest;
import org.example.tieba.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    @PostMapping("/register")
    public Result<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return Result.success(authService.register(request));
    }
}
