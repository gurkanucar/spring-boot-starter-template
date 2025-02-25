package com.gucardev.springbootstartertemplate.domain.user.model.request;

import com.gucardev.springbootstartertemplate.domain.user.enumeration.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
    @Size(max = 255)
    private Role role;

    @Schema(description = "Filter by specific authority", example = "READ_USER")
    @Size(max = 255)
    private String authority;

    @Schema(description = "Filter users created after this date", example = "2024-01-01T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @Schema(description = "Filter users created before this date", example = "2024-12-31T23:59:59")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

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
    private String sortBy = "createdAt";

    @Schema(description = "Sort direction", example = "desc", allowableValues = {"asc", "desc"})
    private String sortDir = "desc";
}
