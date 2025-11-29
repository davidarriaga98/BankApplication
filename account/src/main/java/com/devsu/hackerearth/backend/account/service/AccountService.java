package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;

public interface AccountService {

	public List<AccountDto> getAll();

	public AccountDto getById(Long id) throws ResourceErrorException;

	public AccountDto create(AccountDto accountDto);

	public AccountDto update(AccountDto accountDto);

	public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto);

	public void deleteById(Long id);

	public boolean existsById(Long id);
}
