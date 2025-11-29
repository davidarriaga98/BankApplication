package com.devsu.hackerearth.backend.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.hackerearth.backend.account.exceptions.ResourceErrorException;
import com.devsu.hackerearth.backend.account.model.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleError(ResourceErrorException ex) {
        return ResponseEntity.status(ex.getCode()).body(
                new ErrorResponseDto(
                        "error",
                        ex.getMessage(),
                        ex.getCode().value()));
    }

}
