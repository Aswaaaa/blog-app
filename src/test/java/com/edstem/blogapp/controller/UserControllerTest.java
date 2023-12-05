package com.edstem.blogapp.controller;

import com.edstem.blogapp.config.JwtService;
import com.edstem.blogapp.contract.request.LoginRequest;
import com.edstem.blogapp.contract.request.SignUpRequest;
import com.edstem.blogapp.contract.response.LoginResponse;
import com.edstem.blogapp.contract.response.SignUpResponse;
import com.edstem.blogapp.model.user.Role;
import com.edstem.blogapp.model.user.User;
import com.edstem.blogapp.repository.UserRepository;
import com.edstem.blogapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = {"admin:create", "user:create"})
    public void testSignUp() throws Exception {
        SignUpRequest request =
                SignUpRequest.builder()
                        .name("Admin")
                        .email("admin@example.com")
                        .password("adminPassword")
                        .build();

        SignUpResponse expectedResponse =
                SignUpResponse.builder().name("Admin").email("admin@example.com").build();

        when(userService.register(any(SignUpRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/security/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    @WithMockUser(authorities = {"admin:create", "user:create"})
    public void testLogin() throws Exception {

        LoginRequest request =
                LoginRequest.builder().email("admin@example.com").password("adminPassword").build();

        User mockUser = new User();
        mockUser.setRole(Role.ADMIN);

        LoginResponse expectedResponse =
                LoginResponse.builder().token("testToken").role(Role.ADMIN).build();

        when(userService.authenticate(any(LoginRequest.class))).thenReturn(expectedResponse);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateToken(any(User.class), any(List.class))).thenReturn("testToken");

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);

        mockMvc.perform(
                        post("/security/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}
