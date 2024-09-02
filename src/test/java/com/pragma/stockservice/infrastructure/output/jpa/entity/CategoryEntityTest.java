package com.pragma.stockservice.infrastructure.output.jpa.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Inicializa el validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCategoryEntity() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("Electronics");
        category.setDescription("Various electronic items");

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertEquals(0, violations.size(), "CategoryEntity should not have any validation errors");
    }

    @Test
    void testInvalidCategoryEntityNameTooLong() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("A".repeat(51)); // 51 characters long
        category.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertEquals(1, violations.size(), "CategoryEntity should have one validation error");
        assertEquals("Name cannot exceed 50 characters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCategoryEntityNameContainsInvalidCharacters() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("Electronics123");
        category.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertEquals(1, violations.size(), "CategoryEntity should have one validation error");
        assertEquals("Name must contain only letters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCategoryEntityDescriptionTooLong() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("ValidName");
        category.setDescription("A".repeat(91)); // 91 characters long

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertEquals(1, violations.size(), "CategoryEntity should have one validation error");
        assertEquals("Description cannot exceed 90 characters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCategoryEntityDescriptionContainsInvalidCharacters() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("ValidName");
        category.setDescription("123456");

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertEquals(1, violations.size(), "CategoryEntity should have one validation error");
        assertEquals("Description must contain only letters and spaces, and cannot be empty or just spaces", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidCategoryEntityNameBlank() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName(""); // Blank name
        category.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name must contain only letters")));
        assertEquals(2, violations.size(), "CategoryEntity should have two validation errors");
    }

    @Test
    void testInvalidCategoryEntityDescriptionBlank() {
        // Arrange
        CategoryEntity category = new CategoryEntity();
        category.setName("ValidName");
        category.setDescription(""); // Blank description

        // Act
        Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(category);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Description cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Description must contain only letters and spaces, and cannot be empty or just spaces")));
        assertEquals(2, violations.size(), "CategoryEntity should have two validation errors");
    }
}