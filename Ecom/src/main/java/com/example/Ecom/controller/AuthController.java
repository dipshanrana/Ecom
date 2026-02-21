package com.example.Ecom.controller;

import com.example.Ecom.dto.LoginRequest;
import com.example.Ecom.dto.LoginResponse;
import com.example.Ecom.dto.SignUpRequest;
import com.example.Ecom.dto.SignUpResponse;
import com.example.Ecom.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignUpResponse signup(@RequestBody SignUpRequest signUpRequest){
        return authService.signup(signUpRequest);
    }

    @PostMapping("/login")
    public LoginResponse loginResponse(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

}
