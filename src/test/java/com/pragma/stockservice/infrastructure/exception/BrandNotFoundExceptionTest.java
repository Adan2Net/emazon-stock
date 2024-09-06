package com.pragma.stockservice.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrandNotFoundExceptionTest {

    @Test
    void testBrandNotFoundExceptionMessage() {
        // Arrange
        String expectedMessage = "Brand not found";

        // Act & Assert
        BrandNotFoundException exception = assertThrows(
                BrandNotFoundException.class,
                () -> {
                    throw new BrandNotFoundException(expectedMessage);
                },
                "Expected BrandNotFoundException to be thrown"
        );

        // Assert
        assertEquals(expectedMessage, exception.getMessage(), "Exception message should match the expected message");
    }
}
