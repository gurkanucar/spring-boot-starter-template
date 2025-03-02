package com.gucardev.springbootstartertemplate.domain.transaction.usecase;

import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountBalanceUpdateRequest;
import com.gucardev.springbootstartertemplate.domain.account.usecase.GetAccountByIdUseCase;
import com.gucardev.springbootstartertemplate.domain.account.usecase.UpdateAccountBalanceUseCase;
import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import com.gucardev.springbootstartertemplate.domain.transaction.mapper.TransactionMapper;
import com.gucardev.springbootstartertemplate.domain.transaction.model.dto.TransactionDto;
import com.gucardev.springbootstartertemplate.domain.transaction.model.request.TransactionCreateRequest;
import com.gucardev.springbootstartertemplate.domain.transaction.repository.TransactionRepository;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransactionUseCase implements UseCaseWithParamsAndReturn<TransactionCreateRequest, TransactionDto> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final UpdateAccountBalanceUseCase updateAccountBalanceUseCase;

    @Transactional
    @Override
    public TransactionDto execute(TransactionCreateRequest params) {

        log.debug("Creating new transaction with amount: {}, type: {}", params.getAmount(), params.getType());

        // Get account
        var account = getAccountByIdUseCase.execute(params.getAccountId());

        // Create transaction entity
        Transaction transaction = transactionMapper.toEntity(params);
        transaction.setAccount(account);

        // Update account balance using the dedicated use case
        updateAccountBalanceUseCase.execute(AccountBalanceUpdateRequest.builder()
                .accountId(params.getAccountId())
                .amount(params.getAmount())
                .transactionType(params.getType())
                .build());

//        // Add categories if provided
//        if (params.getCategoryIds() != null && !params.getCategoryIds().isEmpty()) {
//            Set<Category> categories = categoryRepository.findAllById(request.getCategoryIds())
//                    .stream()
//                    .collect(Collectors.toSet());
//
//            if (categories.size() != request.getCategoryIds().size()) {
//                throw new ResourceNotFoundException("One or more categories not found");
//            }
//
//            transaction.setCategories(categories);
//        }

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        log.debug("Transaction created successfully with ID: {}", savedTransaction.getId());
        return transactionMapper.toDto(savedTransaction);
    }
}
