package com.edstem.blogapp.service;

import com.edstem.blogapp.config.JwtService;
import com.edstem.blogapp.contract.request.LoginRequest;
import com.edstem.blogapp.contract.request.SignUpRequest;
import com.edstem.blogapp.contract.response.LoginResponse;
import com.edstem.blogapp.contract.response.SignUpResponse;
import com.edstem.blogapp.exception.EntityAlreadyExistsException;
import com.edstem.blogapp.model.user.Permission;
import com.edstem.blogapp.model.user.Role;
import com.edstem.blogapp.model.user.User;
import com.edstem.blogapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public SignUpResponse register(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException(request.getEmail());
        }
        Role roleName = Role.USER;
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        user = userRepository.save(user);
//        modelMapper.typeMap(User.class, SignUpResponse.class).addMapping(
//                src ->src.getRole(),
//                SignUpResponse::setRole);

        return modelMapper.map(user, SignUpResponse.class);
    }

    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user, user.getRole().toString());
        return LoginResponse.builder()
                .token(jwtToken)
                .role(Role.valueOf(user.getRole().toString()))
                .build();

    }

    @PostConstruct
    public void initDefaultAdminUser() {
        createDefaultAdmin("Sriram", "sriram@gmail.com", "sriramPassword");
        createDefaultAdmin("Aswanth", "aswanth@gmail.com", "aswanthPassword");
    }
        private void createDefaultAdmin (String name, String email, String password){

            User adminUser = new User();
            adminUser.setName(name);
            adminUser.setPassword(passwordEncoder.encode(password));
            adminUser.setEmail(email);
            adminUser.setRole(Role.ADMIN);

            List<Permission> permissions = Arrays.asList(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE
            );
            adminUser.setPermissions(permissions);

            userRepository.save(adminUser);
        }

    }

