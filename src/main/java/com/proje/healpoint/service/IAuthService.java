package com.proje.healpoint.service;

import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;

public interface IAuthService {
    public AuthResponse authenticate(AuthRequest authRequest);
    public String login(String phoneNumber, String password);
    public String loginAsDoctor(String username, String password);
}
