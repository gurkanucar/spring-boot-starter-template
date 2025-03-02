package com.gucardev.springbootstartertemplate.domain.transaction.usecase;

import com.gucardev.springbootstartertemplate.domain.account.repository.specification.AccountSpecification;
import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import com.gucardev.springbootstartertemplate.domain.transaction.mapper.TransactionMapper;
import com.gucardev.springbootstartertemplate.domain.transaction.model.dto.TransactionDto;
import com.gucardev.springbootstartertemplate.domain.transaction.model.request.TransactionsByAccountFilterRequest;
import com.gucardev.springbootstartertemplate.domain.transaction.repository.TransactionRepository;
import com.gucardev.springbootstartertemplate.domain.transaction.repository.specification.TransactionSpecification;
import com.gucardev.springbootstartertemplate.infrastructure.usecase.UseCaseWithParamsAndReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetTransactionsByAccountWIthPaginationUseCase implements UseCaseWithParamsAndReturn<TransactionsByAccountFilterRequest, Page<TransactionDto>> {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public Page<TransactionDto> execute(TransactionsByAccountFilterRequest params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSortDir(), params.getSortBy()));
        Specification<Transaction> spec = Specification
                .where(TransactionSpecification.hasTransactionType(params.getTransactionType()))
                .and(AccountSpecification.byAccountId(params.getAccountId()))
                .and(AccountSpecification.createdBetween(params.getStartDate(), params.getEndDate()));
        Page<Transaction> accountsPage = transactionRepository.findAll(spec, pageable);
        return accountsPage.map(transactionMapper::toDto);
    }

}
