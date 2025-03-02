package com.gucardev.springbootstartertemplate.domain.account.model.dto;

import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceUpdateResponse {

    private UUID id;
    private BigDecimal previousBalance;
    private BigDecimal newBalance;
    private BigDecimal changeAmount;
    private TransactionType transactionType;
    private LocalDateTime updatedAt;

}
