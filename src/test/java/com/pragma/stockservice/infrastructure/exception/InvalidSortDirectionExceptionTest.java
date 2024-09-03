package com.pragma.stockservice.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidSortDirectionExceptionTest {

    @Test
    void testInvalidSortDirectionException() {
        // Act & Assert
        InvalidSortDirectionException exception = assertThrows(
                InvalidSortDirectionException.class,
                () -> {
                    throw new InvalidSortDirectionException();
                },
                "Expected InvalidSortDirectionException to be thrown"
        );

        // Optionally, you can also check if the exception message is null
        assertEquals(null, exception.getMessage(), "The exception message should be null");
    }
}