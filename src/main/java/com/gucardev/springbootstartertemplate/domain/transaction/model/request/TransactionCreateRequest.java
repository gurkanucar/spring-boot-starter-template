package com.gucardev.springbootstartertemplate.domain.transaction.model.request;

import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class TransactionCreateRequest {

    @Schema(description = "Account ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "Account ID is required")
    private UUID accountId;

    @Schema(description = "Transaction amount", example = "100.50", type = "number")
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Schema(description = "Transaction description", example = "Monthly grocery shopping")
    private String description;

    @Schema(description = "Transaction date", example = "2023-04-15T14:30:00")
    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    @Schema(
            description = "Transaction type",
            example = "DEPOSIT",
            type = "string",
            allowableValues = {"DEPOSIT", "WITHDRAWAL", "PAYMENT", "TRANSFER"}
    )
    @Pattern(regexp = "^(DEPOSIT|WITHDRAWAL|PAYMENT|TRANSFER)$", message = "{transactionType.pattern.exception}")
    @NotNull(message = "Transaction type is required")
    private String type;

    public TransactionType getType() {
        return type == null ? null : TransactionType.valueOf(type);
    }

    //    private Set<UUID> categoryIds;
}