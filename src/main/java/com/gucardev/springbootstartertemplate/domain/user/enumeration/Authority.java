package com.gucardev.springbootstartertemplate.domain.user.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum Authority implements GrantedAuthority {
    READ_USER("READ_USER"),
    WRITE_USER("WRITE_USER"),
    DELETE_USER("DELETE_USER"),
    READ_POST("READ_POST"),
    WRITE_POST("WRITE_POST"),
    DELETE_POST("DELETE_POST"),
    READ_COMMENT("READ_COMMENT"),
    WRITE_COMMENT("WRITE_COMMENT"),
    DELETE_COMMENT("DELETE_COMMENT");

    private final String permission;

    @Override
    public String getAuthority() {
        return permission;
    }
}
