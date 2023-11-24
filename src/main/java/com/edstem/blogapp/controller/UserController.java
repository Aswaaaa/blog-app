package com.edstem.blogapp.controller;

import com.edstem.blogapp.contract.request.LoginRequest;
import com.edstem.blogapp.contract.request.SignUpRequest;
import com.edstem.blogapp.contract.response.LoginResponse;
import com.edstem.blogapp.contract.response.SignUpResponse;
import com.edstem.blogapp.exception.AuthenticationFailedException;
import com.edstem.blogapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {

            throw new AuthenticationFailedException("Authentication failed", e);
        }

        return ResponseEntity.ok(userService.authenticate(request));
    }
}
