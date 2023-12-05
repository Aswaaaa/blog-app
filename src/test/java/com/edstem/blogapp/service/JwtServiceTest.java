package com.edstem.blogapp.service;

import com.edstem.blogapp.config.JwtService;
import com.edstem.blogapp.model.user.Permission;
import com.edstem.blogapp.model.user.Role;
import com.edstem.blogapp.model.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class JwtServiceTest {
    private final JwtService jwtService = new JwtService();
    private final User mockUser = Mockito.mock(User.class);
    private final UserDetails mockUserDetails = Mockito.mock(UserDetails.class);

    @Test
    public void testGenerateToken() {
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getRole()).thenReturn(Role.USER);
        when(mockUser.getId()).thenReturn(1L);

        String token = jwtService.generateToken(mockUser, List.of(Permission.USER_READ));
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getRole()).thenReturn(Role.USER);
        when(mockUser.getId()).thenReturn(1L);

        String token = jwtService.generateToken(mockUser, List.of(Permission.USER_READ));
        String username = jwtService.extractUsername(token);

        assertEquals("testUser", username);
    }

    @Test
    public void testIsTokenValid() {
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getRole()).thenReturn(Role.USER);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUserDetails.getUsername()).thenReturn("testUser");

        String token = jwtService.generateToken(mockUser, List.of(Permission.USER_READ));
        boolean isValid = jwtService.isTokenValid(token, mockUserDetails);

        assertTrue(isValid);
    }
}
