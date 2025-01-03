package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IAuthController;
import com.proje.healpoint.jwt.AuthRequest;
import com.proje.healpoint.jwt.AuthResponse;
import com.proje.healpoint.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class AuthControllerImpl implements IAuthController {
    @Autowired
    private IAuthService authService;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse response = authService.authenticate(authRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login-doctor")
    @Override
    public ResponseEntity<?> loginAsDoctor(@RequestBody AuthRequest authRequest) {
        String token = authService.loginAsDoctor(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
