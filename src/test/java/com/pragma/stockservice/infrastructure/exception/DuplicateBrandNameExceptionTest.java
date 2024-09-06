package com.pragma.stockservice.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DuplicateBrandNameExceptionTest {

    @Test
    void testDuplicateBrandNameExceptionMessage() {
        // Arrange
        String expectedMessage = "Duplicate brand name";

        // Act & Assert
        DuplicateBrandNameException exception = assertThrows(
                DuplicateBrandNameException.class,
                () -> {
                    throw new DuplicateBrandNameException(expectedMessage);
                },
                "Expected DuplicateBrandNameException to be thrown"
        );

        // Assert
        assertEquals(expectedMessage, exception.getMessage(), "Exception message should match the expected message");
    }
}
