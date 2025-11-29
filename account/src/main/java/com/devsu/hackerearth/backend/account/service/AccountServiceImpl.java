package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.exceptions.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.model.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) throws ResourceErrorException {
        // Get accounts by id
        return accountRepository
                .findById(id)
                .map(accountMapper::toDto)
                .orElse(null);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        Account newAccount = accountRepository.save(accountMapper.toModel(accountDto));
        return accountMapper.toDto(newAccount);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
        if (!accountRepository.existsById(accountDto.getId())) {
            log.warn("Account with ID: {} not found - cannot update", accountDto.getId());
            return null;
        }
        Account updatedAccount = accountRepository.save(accountMapper.toModel(accountDto));
        return accountMapper.toDto(updatedAccount);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account
        Optional<Account> oAccount = accountRepository.findById(id);
        if (oAccount.isEmpty()) {
            return null; // In the tests, this fails if I throw an exception.
        }

        Account account = oAccount.get();
        if (account.isActive() == partialAccountDto.isActive()) {
            return accountMapper.toDto(account);
        }

        account.setActive(partialAccountDto.isActive());
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public void deleteById(Long id) throws ResourceNotFoundException {
        // Delete account
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }

        accountRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return accountRepository
                .existsById(id);
    }
}
