package com.gucardev.springbootstartertemplate.domain.account.mapper;

import com.gucardev.springbootstartertemplate.domain.account.entity.Account;
import com.gucardev.springbootstartertemplate.domain.account.model.dto.AccountDto;
import com.gucardev.springbootstartertemplate.domain.account.model.request.AccountCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountCreateRequest request);

    AccountDto toDto(Account entity);

}
