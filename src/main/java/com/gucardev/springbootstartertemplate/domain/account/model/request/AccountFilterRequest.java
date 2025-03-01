package com.gucardev.springbootstartertemplate.domain.account.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import com.gucardev.springbootstartertemplate.domain.common.model.request.BaseFilterRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountFilterRequest extends BaseFilterRequest {

    @Schema(description = "Filter by account number", example = "82e923476b5449d5b385e7a6fdf5ce9e")
    @Size(max = 255)
    private String accountNumber;

    @Schema(description = "Filter by username", example = "john")
    @Size(max = 255)
    private String username;

    @Schema(description = "Filter by account type", example = "SAVINGS")
    @Pattern(regexp = "^(CHECKING|SAVINGS|CREDIT|INVESTMENT)$", message = "{role.pattern.exception}")
    private String accountType;

    @Schema(description = "Filter users created after this date", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "Filter users created before this date", example = "2025-12-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public AccountType getAccountType() {
        return accountType == null ? null : AccountType.valueOf(accountType);
    }
}
