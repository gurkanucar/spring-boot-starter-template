package com.gucardev.springbootstartertemplate.domain.user.model.request;

import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    @Schema(
            description = "Username",
            example = "johndoe",
            type = "string",
            minLength = 5,
            maxLength = 25
    )
    @Length(min = 5, max = 25)
    @NotBlank
    private String username;

    @Schema(
            description = "Password",
            example = "pass123",
            type = "string",
            minLength = 5,
            maxLength = 25
    )
    @Length(min = 5, max = 25)
    @NotNull
    private String password;

    @Schema(
            description = "Email Address",
            example = "johndoe@example.com",
            type = "string"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "First Name",
            example = "John",
            type = "string",
            minLength = 2,
            maxLength = 50
    )
    @Length(min = 2, max = 50)
    @NotNull
    private String name;

    @Schema(
            description = "Surname",
            example = "Doe",
            type = "string",
            minLength = 2,
            maxLength = 50
    )
    @Length(min = 2, max = 50)
    private String surname;

    private Set<Role> roles = new HashSet<>();

}
