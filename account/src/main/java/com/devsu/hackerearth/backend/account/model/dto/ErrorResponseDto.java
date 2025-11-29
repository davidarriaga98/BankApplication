package com.devsu.hackerearth.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String status;
    private String message;
    private int code;
}
