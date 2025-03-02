package com.gucardev.springbootstartertemplate.domain.transaction.model.dto;

import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime transactionDate;

    private TransactionType type;

    private UUID accountId;

//    private Set<Category> categories = new HashSet<>();

}
