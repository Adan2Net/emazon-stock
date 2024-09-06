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

class BrandEntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Inicializa el validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBrandEntity() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("Nike");
        brand.setDescription("Leading sports brand");

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertEquals(0, violations.size(), "BrandEntity should not have any validation errors");
    }

    @Test
    void testInvalidBrandEntityNameTooLong() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("A".repeat(51)); // 51 characters long
        brand.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertEquals(1, violations.size(), "BrandEntity should have one validation error");
        assertEquals("Name cannot exceed 50 characters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidBrandEntityNameContainsInvalidCharacters() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("Nike123");
        brand.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertEquals(1, violations.size(), "BrandEntity should have one validation error");
        assertEquals("Name must contain only letters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidBrandEntityDescriptionTooLong() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("Nike");
        brand.setDescription("A".repeat(121)); // 121 characters long

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertEquals(1, violations.size(), "BrandEntity should have one validation error");
        assertEquals("Description cannot exceed 120 characters", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidBrandEntityDescriptionContainsInvalidCharacters() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("Nike");
        brand.setDescription("123456");

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertEquals(1, violations.size(), "BrandEntity should have one validation error");
        assertEquals("Description must contain only letters and spaces, and cannot be empty or just spaces", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidBrandEntityNameBlank() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName(""); // Blank name
        brand.setDescription("Valid description");

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Name must contain only letters")));
        assertEquals(2, violations.size(), "BrandEntity should have two validation errors");
    }

    @Test
    void testInvalidBrandEntityDescriptionBlank() {
        // Arrange
        BrandEntity brand = new BrandEntity();
        brand.setName("Nike");
        brand.setDescription(""); // Blank description

        // Act
        Set<ConstraintViolation<BrandEntity>> violations = validator.validate(brand);

        // Assert
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Description cannot be empty")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Description must contain only letters and spaces, and cannot be empty or just spaces")));
        assertEquals(2, violations.size(), "BrandEntity should have two validation errors");
    }
}