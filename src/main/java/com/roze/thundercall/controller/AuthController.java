package com.roze.thundercall.controller;

import com.roze.thundercall.dto.AuthResponse;
import com.roze.thundercall.dto.RegisterRequest;
import com.roze.thundercall.service.AuthService;
import com.roze.thundercall.utils.ApiResponse;
import com.roze.thundercall.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ok(response);
    }
}
