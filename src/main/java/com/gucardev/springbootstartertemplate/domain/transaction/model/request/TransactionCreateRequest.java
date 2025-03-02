package com.gucardev.springbootstartertemplate.domain.transaction.model.request;

import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {

    @NotNull
    private UUID accountId;

    @NotNull
    private BigDecimal amount;

    private String description;

    @NotNull
    private LocalDateTime transactionDate;

    @NotNull
    private TransactionType type;

    private Set<UUID> categoryIds;

}