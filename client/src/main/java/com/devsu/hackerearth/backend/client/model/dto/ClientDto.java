package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

	private Long id;

	@NonNull
	@Length(max = 30)
	private String dni;

	@NonNull
	@Length(max = 100)
	private String name;

	@NonNull
	@Length(max = 80)
	private String password;

	@NonNull
	@Length(max = 15)
	private String gender;

	@Max(100)
	private int age;

	@NonNull
	@Length(max = 250)
	private String address;

	@NonNull
	@Length(max = 25)
	private String phone;

	private boolean isActive;
}
