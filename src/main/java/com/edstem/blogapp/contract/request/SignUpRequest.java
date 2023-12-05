package com.edstem.blogapp.contract.request;

import com.edstem.blogapp.validation.ValidSignUpPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    @NotNull
    private String name;
    @Email
    private String email;
    @ValidSignUpPassword
    private String password;
}
