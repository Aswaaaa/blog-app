package com.edstem.blogapp.contract.request;

import com.edstem.blogapp.validation.ValidSignUpPassword;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpRequest {
    @Email
    private String email;
    @ValidSignUpPassword
    private String password;
}
