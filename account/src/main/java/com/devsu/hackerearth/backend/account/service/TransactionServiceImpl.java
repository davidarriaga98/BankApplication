package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientFromMicroserviceDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.mapper.TransactionMapper;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.config.MicroserviceCommunication;
import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.exceptions.ResourceNotFoundException;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;
    private final MicroserviceCommunication microserviceCommunication;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper,
            AccountService accountService,
            MicroserviceCommunication microserviceCommunication) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
        this.microserviceCommunication = microserviceCommunication;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) throws ResourceNotFoundException {
        // Get transactions by id
        return transactionRepository
                .findById(id)
                .map(transactionMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) throws ResourceErrorException {
        // Create transaction
        AccountDto accountDto = accountService.getById(transactionDto.getAccountId());
        if (accountDto == null)
            throw new ResourceErrorException("Account not found", HttpStatus.NOT_FOUND);

        if (!accountDto.isActive())
            throw new ResourceErrorException("Account no active", HttpStatus.BAD_REQUEST);

        TransactionDto lastTransaction = getLastByAccountId(accountDto.getId());
        double modifiedAmount = (lastTransaction == null ? accountDto.getInitialAmount() : lastTransaction.getBalance())
                + transactionDto.getAmount();
        if (modifiedAmount < 0)
            throw new ResourceErrorException("Unavailable Balance", HttpStatus.BAD_REQUEST);

        transactionDto.setBalance(modifiedAmount);
        transactionDto.setDate(new Date());
        Transaction savedTransaction = transactionRepository.save(transactionMapper.toModel(transactionDto));
        return transactionMapper.toDto(savedTransaction);
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        ClientFromMicroserviceDto clientInfo = microserviceCommunication.obtainClientInfo(clientId);
        String clientName = clientInfo == null ? clientId.toString() : clientInfo.getName();
        return transactionRepository
                .findByDateBetweenAndAccount_ClientId(dateTransactionStart, dateTransactionEnd, clientId)
                .stream()
                .map(tran -> {
                    return new BankStatementDto(
                            tran.getDate(),
                            clientName,
                            tran.getAccount().getNumber(),
                            tran.getAccount().getType(),
                            tran.getAccount().getInitialAmount(),
                            tran.getAccount().isActive(),
                            tran.getType(),
                            tran.getAmount(),
                            tran.getBalance());
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        Optional<Transaction> oTransaction = transactionRepository.findTopByAccountIdOrderByDateDesc(accountId);
        if (oTransaction.isEmpty())
            return null;
        else
            return transactionMapper.toDto(oTransaction.get());
    }

    public boolean existsById(Long id) {
        return transactionRepository.existsById(id);
    }
}
