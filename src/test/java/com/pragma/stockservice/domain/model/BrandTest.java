package com.pragma.stockservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedName = "Test Brand";
        String expectedDescription = "Test Description";

        // Act
        Brand brand = new Brand(expectedName, expectedDescription);

        // Assert
        assertEquals(expectedName, brand.getName(), "The brand name should match the expected name");
        assertEquals(expectedDescription, brand.getDescription(), "The brand description should match the expected description");
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Brand brand = new Brand("Initial Name", "Initial Description");

        // Act
        brand.setName("Updated Name");
        brand.setDescription("Updated Description");
        brand.setId(1L);

        // Assert
        assertEquals("Updated Name", brand.getName(), "The brand name should be updated");
        assertEquals("Updated Description", brand.getDescription(), "The brand description should be updated");
        assertEquals(1L, brand.getId(), "The brand ID should be updated");
    }
}
