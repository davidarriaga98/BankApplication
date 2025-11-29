package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.exceptions.ResourceNotFoundException;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final Logger log = LoggerFactory.getLogger(AccountController.class);
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		// api/accounts
		// Get all accounts
		return ResponseEntity.ok(accountService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> get(@PathVariable Long id) throws ResourceErrorException {
		// api/accounts/{id}
		// Get accounts by id
		AccountDto account = accountService.getById(id);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(account);
	}

	@PostMapping
	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		// api/accounts
		// Create accounts
		log.info("Creating account {}", accountDto);
		AccountDto newAccount = accountService.create(accountDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		// api/accounts/{id}
		// Update accounts
		accountDto.setId(id);
		AccountDto updatedAccountDto = accountService.update(accountDto);
		if (updatedAccountDto == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatedAccountDto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		// api/accounts/{id}
		// Partial update accounts
		AccountDto account = accountService.partialUpdate(id, partialAccountDto);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// api/accounts/{id}
		// Delete accounts
		try {
			accountService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
