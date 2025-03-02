package com.gucardev.springbootstartertemplate.domain.account.usecase;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.account.repository.AccountRepository;
import com.gucardev.springbootstartertemplate.infrastructure.exception.ExceptionMessage;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.gucardev.springbootstartertemplate.infrastructure.exception.helper.ExceptionUtil.buildException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAccountByIdUseCase implements UseCaseWithParamsAndReturn<UUID, Account> {

    private final AccountRepository accountRepository;

    @Override
    public Account execute(UUID uuid) {
        return accountRepository.findById(uuid)
                .orElseThrow(() -> buildException(ExceptionMessage.NOT_FOUND_EXCEPTION));
    }
}
