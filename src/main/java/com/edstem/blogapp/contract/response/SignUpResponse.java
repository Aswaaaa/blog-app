package com.edstem.blogapp.contract.response;

import com.edstem.blogapp.model.user.Role;
import lombok.Data;

@Data
public class SignUpResponse {
    private String name;
    private String email;
    private Role role;
}
