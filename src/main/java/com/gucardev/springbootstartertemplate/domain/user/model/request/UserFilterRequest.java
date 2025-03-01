package com.gucardev.springbootstartertemplate.domain.user.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterRequest {

    @Schema(description = "Filter by name", example = "John")
    @Size(max = 255)
    private String name;

    @Schema(description = "Filter by surname", example = "Doe")
    @Size(max = 255)
    private String surname;

    @Schema(description = "Filter by email", example = "john.doe@example.com")
    @Size(max = 255)
    private String email;

    @Schema(description = "Filter by role", example = "ADMIN")
    @Pattern(regexp = "^(ADMIN|MODERATOR|USER)$", message = "{role.pattern.exception}")
    private String role;

    @Schema(description = "Filter by specific authority", example = "READ_USER")
    @Size(max = 255)
    private String authority;

    @Schema(description = "Filter users created after this date", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "Filter users created before this date", example = "2025-12-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "Page number (starts from 0)", example = "0")
    @NotNull
    @Min(0)
    @Max(99999)
    private int page = 0;

    @Schema(description = "Number of records per page", example = "10")
    @NotNull
    @Min(1)
    @Max(150)
    @Positive
    private int size = 10;

    @Schema(description = "Sort by field", example = "username")
    private String sortBy = "createdDate";

    @Schema(description = "Sort direction", example = "desc", allowableValues = {"asc", "desc"})
    @Pattern(regexp = "^(asc|desc)$", message = "{sort.direction.pattern.exception}")
    private String sortDir = "desc";

    public Role getRole() {
        return role == null ? null : Role.valueOf(role);
    }
}
