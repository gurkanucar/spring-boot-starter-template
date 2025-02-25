package com.gucardev.springbootstartertemplate.domain.user.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private UUID id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String name;
    private String surname;
    private Set<Role> roles = new HashSet<>();
    private Set<String> authorities = new HashSet<>();

}
