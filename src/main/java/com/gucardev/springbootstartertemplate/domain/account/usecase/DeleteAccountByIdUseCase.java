package com.gucardev.springbootstartertemplate.domain.account.usecase;

import com.gucardev.springbootstartertemplate.domain.account.repository.AccountRepository;
import com.gucardev.springbootstartertemplate.infrastructure.constants.Constants;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteAccountByIdUseCase implements UseCaseWithParams<UUID> {

    private final AccountRepository accountRepository;

    @Override
    public void execute(UUID uuid) {
        accountRepository.softDelete(uuid, Constants.DEFAULT_AUDITOR);
    }

}
