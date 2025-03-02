package com.gucardev.springbootstartertemplate.domain.transaction.mapper;

import com.gucardev.springbootstartertemplate.domain.transaction.entity.Transaction;
import com.gucardev.springbootstartertemplate.domain.transaction.model.dto.TransactionDto;
import com.gucardev.springbootstartertemplate.domain.transaction.model.request.TransactionCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    TransactionDto toDto(Transaction entity);
}
