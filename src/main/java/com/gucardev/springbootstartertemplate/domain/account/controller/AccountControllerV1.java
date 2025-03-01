package com.gucardev.springbootstartertemplate.domain.account.controller;

import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountDto;
import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountDtoWithUser;
import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountCreateRequest;
import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountFilterRequest;
import com.gucardev.springbootstartertemplate.domain.account.usecase.CreateAccountUseCase;
import com.gucardev.springbootstartertemplate.domain.account.usecase.GetAccountAndUserDtoByIdUseCase;
import com.gucardev.springbootstartertemplate.domain.account.usecase.SearchAccountsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Controller", description = "Controller for account operations")
public class AccountControllerV1 {

    private final CreateAccountUseCase createAccountUseCase;
    private final SearchAccountsUseCase searchAccountsUseCase;
    private final GetAccountAndUserDtoByIdUseCase getAccountAndUserDtoByIdUseCase;

    @Operation(
            summary = "Create a new account",
            description = "This api creates a new account and return created account"
    )
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        return ResponseEntity.ok(createAccountUseCase.execute(accountCreateRequest));
    }

    @Operation(
            summary = "Search accounts",
            description = "This api allows you to search and filter accounts with pagination"
    )
    @GetMapping("/search")
    public ResponseEntity<Page<AccountDto>> searchAccounts(@Valid @ParameterObject AccountFilterRequest filterRequest) {
        return ResponseEntity.ok(searchAccountsUseCase.execute(filterRequest));
    }

    @Operation(
            summary = "Get account by id",
            description = "This api retrieves account by id"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<AccountDtoWithUser> getAccountById(@Valid @NotNull @PathVariable UUID uuid) {
        return ResponseEntity.ok(getAccountAndUserDtoByIdUseCase.execute(uuid));
    }


}
