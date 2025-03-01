package com.gucardev.springbootstartertemplate.domain.account.model.dto;


import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import com.gucardev.springbootstartertemplate.domain.common.model.dto.BaseDto;
import com.gucardev.springbootstartertemplate.domain.user.model.dto.UserDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoWithUser extends BaseDto {

    private UUID id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private UserDto user;

}
