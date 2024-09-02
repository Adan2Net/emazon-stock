package com.pragma.stockservice.infrastructure.exceptionhandler;

import com.pragma.stockservice.domain.model.error.ErrorResponse;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalControllerAdviceTest {

    private final GlobalControllerAdvice globalControllerAdvice = new GlobalControllerAdvice();

    @Test
    void testHandleCategoryNotFoundException() {
        ErrorResponse response = globalControllerAdvice.handleCategoryNotFoundException();

        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getCode());
        assertEquals(ExceptionResponse.CATEGORY_NOT_FOUND.getMessage(), response.getMessage());
        assertEquals(Collections.singletonList("The category you are trying to access does not exist."), response.getDetails());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getTimestamp().getDayOfYear()); // Check date time as it is dynamic
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid input");
        ErrorResponse response = globalControllerAdvice.handleIllegalArgumentException(exception);

        assertEquals(ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getCode(), response.getCode());
        assertEquals(ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getMessage(), response.getMessage());
        assertEquals(Collections.singletonList("Validation failed for the input parameters. Invalid input"), response.getDetails());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getTimestamp().getDayOfYear()); // Check date time as it is dynamic
    }

    @Test
    void testHandleDuplicateCategoryNameException() {
        DuplicateCategoryNameException exception = new DuplicateCategoryNameException("Duplicate name");
        ErrorResponse response = globalControllerAdvice.handleDuplicateCategoryNameException(exception);

        assertEquals(HttpStatus.CONFLICT.toString(), response.getCode());
        assertEquals(ExceptionResponse.DUPLICATE_CATEGORY_NAME.getMessage(), response.getMessage());
        assertEquals(Collections.singletonList("A category with this name already exists. Please choose a different name."), response.getDetails());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getTimestamp().getDayOfYear()); // Check date time as it is dynamic
    }

    @Test
    void testHandleGenericError() {
        Exception exception = new Exception("An unexpected error");
        ErrorResponse response = globalControllerAdvice.handleGenericError(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getCode());
        assertEquals(ExceptionResponse.GENERIC_ERROR.getMessage(), response.getMessage());
        assertEquals(Collections.singletonList("An unexpected error"), response.getDetails());
        assertEquals(LocalDateTime.now().getDayOfYear(), response.getTimestamp().getDayOfYear()); // Check date time as it is dynamic
    }
}
