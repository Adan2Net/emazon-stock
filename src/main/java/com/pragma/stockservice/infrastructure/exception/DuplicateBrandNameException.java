package com.pragma.stockservice.infrastructure.exception;

public class DuplicateBrandNameException extends RuntimeException {
    public DuplicateBrandNameException(String message) {
        super(message);
    }
}
