package com.pragma.stockservice.infrastructure.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryNotFoundExceptionTest {

    @Test
    void testCategoryNotFoundExceptionMessage() {
        // Arrange
        String expectedMessage = "Category not found";

        // Act & Assert
        CategoryNotFoundException exception = assertThrows(
                CategoryNotFoundException.class,
                () -> {
                    throw new CategoryNotFoundException(expectedMessage);
                },
                "Expected CategoryNotFoundException to be thrown"
        );

        // Assert
        assertEquals(expectedMessage, exception.getMessage(), "Exception message should match the expected message");
    }
}