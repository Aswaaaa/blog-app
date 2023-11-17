package com.edstem.blogapp.service;

import com.edstem.blogapp.config.JwtService;
import com.edstem.blogapp.contract.request.LoginRequest;
import com.edstem.blogapp.contract.request.SignUpRequest;
import com.edstem.blogapp.contract.response.LoginResponse;
import com.edstem.blogapp.contract.response.SignUpResponse;
import com.edstem.blogapp.exception.EntityAlreadyExistsException;
import com.edstem.blogapp.model.Role;
import com.edstem.blogapp.model.User;
import com.edstem.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public SignUpResponse register(SignUpRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EntityAlreadyExistsException(request.getEmail());
        }
        Role roleName = Role.ADMIN;
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignUpResponse.class);
    }
    public LoginResponse authenticate(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user,user.getRole().toString());
        return LoginResponse.builder().token(jwtToken).build();

    }
}
