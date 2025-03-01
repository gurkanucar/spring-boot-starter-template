package com.gucardev.springbootstartertemplate.domain.account.model.request;

import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequest {


    @NotNull(message = "Initial balance is required")
    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance;

    @Schema(
            description = "account type",
            example = "SAVINGS",
            type = "string"
    )
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "User ID is required")
    private UUID userId;

}
