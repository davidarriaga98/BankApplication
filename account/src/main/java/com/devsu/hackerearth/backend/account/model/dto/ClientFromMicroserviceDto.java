package com.devsu.hackerearth.backend.account.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientFromMicroserviceDto {
	private Long id;
	private String dni;
	private String name;
}
