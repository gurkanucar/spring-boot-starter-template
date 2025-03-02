package com.gucardev.springbootstartertemplate.domain.account.model.request;

import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceUpdateRequest {

    @NotNull
    private UUID accountId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private TransactionType transactionType;
}
