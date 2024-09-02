package com.pragma.stockservice.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    CATEGORY_NOT_FOUND("404 Not Found", "Category not found."),
    INVALID_CATEGORY_PARAMETERS("400 Bad Request", "Invalid category parameters."),
    DUPLICATE_CATEGORY_NAME("409 Conflict", "Category with this name already exists."),
    GENERIC_ERROR("500", "An unexpected error occurred.");

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
