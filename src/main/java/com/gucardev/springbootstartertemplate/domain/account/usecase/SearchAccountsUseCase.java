package com.gucardev.springbootstartertemplate.domain.account.usecase;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.account.mapper.AccountMapper;
import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountDto;
import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountFilterRequest;
import com.gucardev.springbootstartertemplate.domain.account.repository.AccountRepository;
import com.gucardev.springbootstartertemplate.domain.account.repository.specification.AccountSpecification;
import com.gucardev.springbootstartertemplate.domain.common.enumeration.DeletedStatus;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchAccountsUseCase implements UseCaseWithParamsAndReturn<AccountFilterRequest, Page<AccountDto>> {


    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Page<AccountDto> execute(AccountFilterRequest params) {

        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSortDir(), params.getSortBy()));

        Specification<Account> spec = Specification
                .where(AccountSpecification.hasUsernameLike(params.getUsername()))
                .and(AccountSpecification.hasAccountNumberLike(params.getAccountNumber()))
                .and(AccountSpecification.hasAccountType(params.getAccountType()))
                .and(AccountSpecification.createdBetween(params.getStartDate(), params.getEndDate()))
                .and(AccountSpecification.deleted(DeletedStatus.DELETED_FALSE));

        Page<Account> accountsPage = accountRepository.findAll(spec, pageable);
        return accountsPage.map(accountMapper::toDto);
    }
}
