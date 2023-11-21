package com.edstem.blogapp.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;



public enum Role {
    ADMIN,
    USER
//    ADMIN(List.of(ADMIN_READ,
//            ADMIN_UPDATE,
//            ADMIN_DELETE,
//            ADMIN_CREATE)),
//    USER(List.of(USER_READ));
//
//    @Getter
//    private final List<Permission> permissions;
//
//        public List<SimpleGrantedAuthority> getAuthorities() {
//      var authorities =  getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.name()))
//              .toList();
//      authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//      return authorities;
//    }
//    Role(List<Permission> permissions) {
//        this.permissions = permissions;
//    }

}
