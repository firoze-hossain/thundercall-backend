package com.roze.thundercall.service;

import com.roze.thundercall.dto.AuthResponse;
import com.roze.thundercall.dto.LoginRequest;
import com.roze.thundercall.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
