package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	private Long id;

	@NonNull
	private Date date;

	@NonNull
	private String type;
	private double amount;
	private double balance;

	@NonNull
	private Long accountId;
}
