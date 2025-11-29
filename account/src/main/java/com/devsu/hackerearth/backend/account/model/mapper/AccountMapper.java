package com.devsu.hackerearth.backend.account.model.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@Component
public class AccountMapper {
    public Account toModel(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setActive(dto.isActive());
        account.setInitialAmount(dto.getInitialAmount());
        account.setClientId(dto.getClientId());
        account.setNumber(dto.getNumber());
        account.setType(dto.getType());
        return account;
    }

    public AccountDto toDto(Account model) {
        AccountDto dto = new AccountDto();
        dto.setId(model.getId());
        dto.setActive(model.isActive());
        dto.setInitialAmount(model.getInitialAmount());
        dto.setClientId(model.getClientId());
        dto.setNumber(model.getNumber());
        dto.setType(model.getType());
        return dto;
    }
}
