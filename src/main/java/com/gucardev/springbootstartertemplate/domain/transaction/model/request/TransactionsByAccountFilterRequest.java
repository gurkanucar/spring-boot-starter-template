package com.gucardev.springbootstartertemplate.domain.transaction.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gucardev.springbootstartertemplate.domain.common.model.request.BaseFilterRequest;
import com.gucardev.springbootstartertemplate.domain.transaction.enumeration.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsByAccountFilterRequest extends BaseFilterRequest {

    @Schema(description = "Filter by account id", example = "5f976093-a867-4fa3-9ec8-c9f6e00f800c")
    @Size(max = 255)
    private String accountId;

    @Schema(description = "Filter by account type", example = "DEPOSIT")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAWAL|TRANSFER|PAYMENT)$", message = "{role.pattern.exception}")
    private String transactionType;

    @Schema(description = "Filter transactions created after this date", example = "2024-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "Filter transactions created before this date", example = "2025-12-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public TransactionType getTransactionType() {
        return transactionType == null ? null : TransactionType.valueOf(transactionType);
    }

    public UUID getAccountId() {
        return UUID.fromString(accountId);
    }
}
