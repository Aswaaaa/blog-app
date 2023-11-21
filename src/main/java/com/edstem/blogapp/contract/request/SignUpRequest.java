package com.edstem.blogapp.contract.request;

import com.edstem.blogapp.model.user.Role;
import com.edstem.blogapp.validation.ValidSignUpPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpRequest {
    @Email
    private String email;
    @ValidSignUpPassword
    private String password;

}
