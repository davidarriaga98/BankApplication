package com.devsu.hackerearth.backend.account.model.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	private Long id;

	@NonNull
	@Length(max = 50)
	private String number;

	@NonNull
	@Length(max = 50)
	private String type;

	private double initialAmount;
	private boolean isActive;

	@NonNull
	private Long clientId;
}
