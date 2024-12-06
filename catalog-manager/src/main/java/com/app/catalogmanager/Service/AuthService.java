package com.app.catalogmanager.Service;

import com.app.catalogmanager.DTO.request.AuthRequest;
import com.app.catalogmanager.DTO.request.RegisterRequest;
import com.app.catalogmanager.DTO.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    AuthResponse logout();
} 