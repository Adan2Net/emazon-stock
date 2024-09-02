package com.pragma.stockservice.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DuplicateCategoryNameExceptionTest {

    @Test
    public void testDuplicateCategoryNameExceptionMessage() {
        // Arrange
        String expectedMessage = "Duplicate category name";

        // Act & Assert
        DuplicateCategoryNameException exception = assertThrows(
                DuplicateCategoryNameException.class,
                () -> {
                    throw new DuplicateCategoryNameException(expectedMessage);
                },
                "Expected DuplicateCategoryNameException to be thrown"
        );

        // Assert
        assertEquals(expectedMessage, exception.getMessage(), "Exception message should match the expected message");
    }
}