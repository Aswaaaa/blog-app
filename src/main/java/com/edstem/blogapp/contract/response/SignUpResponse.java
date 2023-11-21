package com.edstem.blogapp.contract.response;

import com.edstem.blogapp.model.user.Role;
import lombok.Data;

@Data
public class SignUpResponse {
    private String email;
    private String password;
    private Role role;
}
