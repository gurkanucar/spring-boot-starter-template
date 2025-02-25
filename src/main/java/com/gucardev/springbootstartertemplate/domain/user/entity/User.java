package com.gucardev.springbootstartertemplate.domain.user.entity;

import com.gucardev.springbootstartertemplate.domain.common.entity.BaseEntity;
import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>(roles);
        roles.forEach(role ->
                authorities.addAll(role.getAuthorities())
        );
        return authorities;
    }
}