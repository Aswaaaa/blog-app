package com.edstem.blogapp.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
    public AuthenticationFailedException(String message,Throwable cause) {
        super(message, cause);
    }
}
