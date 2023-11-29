package com.edstem.blogapp.model.user;

import lombok.Getter;

import java.util.List;

import static com.edstem.blogapp.model.user.Permission.ADMIN_CREATE;
import static com.edstem.blogapp.model.user.Permission.ADMIN_DELETE;
import static com.edstem.blogapp.model.user.Permission.ADMIN_READ;
import static com.edstem.blogapp.model.user.Permission.ADMIN_UPDATE;
import static com.edstem.blogapp.model.user.Permission.USER_READ;

public enum Role {
    ADMIN(List.of(ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE)),
    USER(List.of(USER_READ));

    @Getter
    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
