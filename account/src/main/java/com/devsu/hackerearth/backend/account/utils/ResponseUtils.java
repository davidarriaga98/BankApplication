package com.devsu.hackerearth.backend.account.utils;

import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.model.dto.ErrorResponseDto;

public class ResponseUtils {
    public static ResponseEntity<ErrorResponseDto> generateResponseFromException(Exception ex) {
        if (ex instanceof ResourceErrorException) {
            ResourceErrorException resourceEx = (ResourceErrorException) ex;
            return ResponseEntity.status(resourceEx.getCode()).body(
                    new ErrorResponseDto(
                            "error",
                            ex.getMessage(),
                            resourceEx.getCode().value()));
        } else {
            return ResponseEntity.status(500).body(
                    new ErrorResponseDto(
                            "error",
                            ex.getMessage(),
                            500));
        }
    }
}
