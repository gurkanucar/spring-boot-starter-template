package com.gucardev.springbootstartertemplate.domain.user.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN", Set.of(
            Authority.READ_USER, Authority.WRITE_USER, Authority.DELETE_USER,
            Authority.READ_POST, Authority.WRITE_POST, Authority.DELETE_POST,
            Authority.READ_COMMENT, Authority.WRITE_COMMENT, Authority.DELETE_COMMENT
    )),

    MODERATOR("ROLE_MODERATOR", Set.of(
            Authority.READ_USER,
            Authority.READ_POST, Authority.WRITE_POST,
            Authority.READ_COMMENT, Authority.WRITE_COMMENT, Authority.DELETE_COMMENT
    )),

    USER("ROLE_USER", Set.of(
            Authority.READ_POST,
            Authority.READ_COMMENT, Authority.WRITE_COMMENT
    ));

    private final String roleName;
    private final Set<Authority> authorities;

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>(authorities);
    }
}
