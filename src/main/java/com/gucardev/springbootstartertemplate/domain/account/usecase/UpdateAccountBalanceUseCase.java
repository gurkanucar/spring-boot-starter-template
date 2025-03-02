package com.gucardev.springbootstartertemplate.domain.account.usecase;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountBalanceUpdateResponse;
import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountBalanceUpdateRequest;
import com.gucardev.springbootstartertemplate.domain.account.repository.AccountRepository;
import com.gucardev.springbootstartertemplate.infrastructure.exception.ExceptionMessage;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.gucardev.springbootstartertemplate.infrastructure.exception.helper.ExceptionUtil.buildException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateAccountBalanceUseCase implements UseCaseWithParamsAndReturn<AccountBalanceUpdateRequest, AccountBalanceUpdateResponse> {

    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public AccountBalanceUpdateResponse execute(AccountBalanceUpdateRequest params) {
        log.info("Updating account balance for account ID: {}, amount: {}, type: {}",
                params.getAccountId(), params.getAmount(), params.getTransactionType());

        // Get account
        Account account = accountRepository.findByIdWithPessimisticLock(params.getAccountId())
                .orElseThrow(() -> buildException(ExceptionMessage.NOT_FOUND_EXCEPTION, params.getAccountId()));

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance;

        // Calculate new balance based on transaction type
        switch (params.getTransactionType()) {
            case DEPOSIT:
                newBalance = currentBalance.add(params.getAmount());
                break;
            case WITHDRAWAL:
            case PAYMENT:
                newBalance = currentBalance.subtract(params.getAmount());
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw buildException(ExceptionMessage.INSUFFICIENT_FUNDS_EXCEPTION);
                }
                break;
            case TRANSFER:
                // For transfers, this handles only the source account
                newBalance = currentBalance.subtract(params.getAmount());
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw buildException(ExceptionMessage.INSUFFICIENT_FUNDS_EXCEPTION);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + params.getTransactionType());
        }

        // Update and save account
        account.setBalance(newBalance);
        accountRepository.save(account);

        log.info("Account balance updated successfully for account ID: {}, new balance: {}",
                account.getId(), newBalance);

        // Build and return response
        return AccountBalanceUpdateResponse.builder()
                .id(account.getId())
                .previousBalance(currentBalance)
                .newBalance(newBalance)
                .changeAmount(params.getAmount())
                .transactionType(params.getTransactionType())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
