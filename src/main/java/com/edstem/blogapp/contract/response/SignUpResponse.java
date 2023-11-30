package com.edstem.blogapp.contract.response;

import com.edstem.blogapp.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
    private String name;
    private String email;
    private Role role;
}
