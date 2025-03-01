package com.gucardev.springbootstartertemplate.domain.account.usecase;

import com.gucardev.springbootstartertemplate.domain.account.mapper.AccountMapper;
import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountDtoWithUser;
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
public class GetAccountAndUserDtoByIdUseCase implements UseCaseWithParamsAndReturn<UUID, AccountDtoWithUser> {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDtoWithUser execute(UUID uuid) {
        return accountRepository.findById(uuid).map(accountMapper::toDtoWithUser).orElseThrow(() -> buildException(ExceptionMessage.NOT_FOUND_EXCEPTION));
    }
}
