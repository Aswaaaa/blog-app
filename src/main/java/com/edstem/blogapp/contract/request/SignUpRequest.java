package com.edstem.blogapp.contract.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpRequest {
    @Email
    private String email;
    private String password;
}
