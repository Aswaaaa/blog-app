package com.edstem.blogapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.blogapp.config.JwtService;
import com.edstem.blogapp.contract.request.LoginRequest;
import com.edstem.blogapp.contract.request.SignUpRequest;
import com.edstem.blogapp.contract.response.LoginResponse;
import com.edstem.blogapp.contract.response.SignUpResponse;
import com.edstem.blogapp.model.user.Permission;
import com.edstem.blogapp.model.user.Role;
import com.edstem.blogapp.model.user.User;
import com.edstem.blogapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock private ModelMapper modelMapper;
    private UserService userService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock  private AuthenticationManager authenticationManager;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder,jwtService,authenticationManager,modelMapper);
    }
    @Test
    public void testRegister() {
        SignUpRequest request = SignUpRequest.builder()
                .name("Test User")
                .email("test@test.com")
                .password("TestPassword")
                .build();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .permissions(Arrays.asList(Permission.USER_READ))
                .build();

        SignUpResponse expectedResponse = SignUpResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, SignUpResponse.class)).thenReturn(expectedResponse);

        SignUpResponse response = userService.register(request);

        assertEquals(expectedResponse, response);
    }
    @Test
    public void testAuthenticate() {
        LoginRequest request = LoginRequest.builder()
                .email("test@test.com")
                .password("TestPassword")
                .build();

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .permissions(Arrays.asList(Permission.USER_READ))
                .build();

        String jwtToken = "testToken";
        LoginResponse expectedResponse = LoginResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user, user.getRole().getPermissions())).thenReturn(jwtToken);

        LoginResponse response = userService.authenticate(request);

        assertEquals(expectedResponse, response);
    }
    @Test
    public void testInitDefaultAdminUsers() {
        User adminUser = User.builder()
                .name("Sriram")
                .email("sriram@example.com")
                .password(passwordEncoder.encode("Password"))
                .role(Role.ADMIN)
                .permissions(Arrays.asList(Permission.ADMIN_READ, Permission.ADMIN_UPDATE, Permission.ADMIN_CREATE, Permission.ADMIN_DELETE))
                .build();

        when(userRepository.findByEmail("sriram@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(adminUser);

        userService.initDefaultAdminUsers();

        verify(userRepository, times(2)).findByEmail(anyString());
        verify(userRepository, times(2)).save(any(User.class));
    }


}
