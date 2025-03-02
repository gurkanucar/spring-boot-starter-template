package com.gucardev.springbootstartertemplate.domain.transaction.controller;

import com.gucardev.springbootstartertemplate.domain.transaction.model.dto.TransactionDto;
import com.gucardev.springbootstartertemplate.domain.transaction.model.request.TransactionCreateRequest;
import com.gucardev.springbootstartertemplate.domain.transaction.model.request.TransactionsByAccountFilterRequest;
import com.gucardev.springbootstartertemplate.domain.transaction.usecase.CreateTransactionUseCase;
import com.gucardev.springbootstartertemplate.domain.transaction.usecase.GetTransactionsByAccountWIthPaginationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller", description = "Controller for transactional operations")
public class TransactionControllerV1 {

    private final GetTransactionsByAccountWIthPaginationUseCase getTransactionsByAccountWIthPaginationUseCase;
    private final CreateTransactionUseCase createTransactionUseCase;

    @Operation(
            summary = "Search transactions by account id",
            description = "This api allows you to search and filter transactions by account id with pagination"
    )
    @GetMapping("/search")
    public ResponseEntity<Page<TransactionDto>> searchTransactionsByAccount(@Valid @ParameterObject TransactionsByAccountFilterRequest filterRequest) {
        return ResponseEntity.ok(getTransactionsByAccountWIthPaginationUseCase.execute(filterRequest));
    }

    @Operation(
            summary = "Create a new transaction",
            description = "This api creates a new transaction and return created transaction"
    )
    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionCreateRequest transactionCreateRequest) {
        return ResponseEntity.ok(createTransactionUseCase.execute(transactionCreateRequest));
    }


}
