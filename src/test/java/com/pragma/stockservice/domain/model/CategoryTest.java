package com.pragma.stockservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedName = "Test Category";
        String expectedDescription = "Test Description";

        // Act
        Category category = new Category(expectedName, expectedDescription);

        // Assert
        assertEquals(expectedName, category.getName(), "The category name should match the expected name");
        assertEquals(expectedDescription, category.getDescription(), "The category description should match the expected description");
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Category category = new Category("Initial Name", "Initial Description");

        // Act
        category.setName("Updated Name");
        category.setDescription("Updated Description");
        category.setId(1L);

        // Assert
        assertEquals("Updated Name", category.getName(), "The category name should be updated");
        assertEquals("Updated Description", category.getDescription(), "The category description should be updated");
        assertEquals(1L, category.getId(), "The category ID should be updated");
    }
}
