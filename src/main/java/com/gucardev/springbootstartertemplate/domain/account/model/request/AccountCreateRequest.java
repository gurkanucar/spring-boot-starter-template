package com.gucardev.springbootstartertemplate.domain.account.model.request;

import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreateRequest {

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Initial balance is required")
    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "User ID is required")
    private UUID userId;

}
