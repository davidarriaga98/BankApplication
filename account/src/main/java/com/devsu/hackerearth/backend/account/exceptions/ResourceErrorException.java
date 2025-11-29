package com.devsu.hackerearth.backend.account.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class ResourceErrorException extends RuntimeException {
    @Getter
    private HttpStatus code;

    public ResourceErrorException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
