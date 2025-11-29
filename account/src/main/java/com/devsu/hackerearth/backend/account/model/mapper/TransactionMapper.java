package com.devsu.hackerearth.backend.account.model.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

@Component
public class TransactionMapper {
    public Transaction toModel(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setBalance(dto.getBalance());

        Account account = new Account();
        account.setId(dto.getAccountId());
        transaction.setAccount(account);

        return transaction;
    }

    public TransactionDto toDto(Transaction model) {
        TransactionDto dto = new TransactionDto();
        dto.setId(model.getId());
        dto.setDate(model.getDate());
        dto.setType(model.getType());
        dto.setAmount(model.getAmount());
        dto.setBalance(model.getBalance());
        if (model.getAccount() != null) {
            dto.setAccountId(model.getAccount().getId());
        }
        return dto;
    }
}
