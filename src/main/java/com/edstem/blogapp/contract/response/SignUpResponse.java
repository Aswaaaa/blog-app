package com.edstem.blogapp.contract.response;

import com.edstem.blogapp.model.user.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponse {
    private String name;
    private String email;
    private Role role;
}
