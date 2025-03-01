package com.gucardev.springbootstartertemplate.domain.account.model.dto;


import com.gucardev.springbootstartertemplate.domain.account.enumeration.AccountType;
import com.gucardev.springbootstartertemplate.domain.common.model.dto.BaseDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto extends BaseDto {

    private UUID id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private UUID userId;

}
