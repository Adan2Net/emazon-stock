package com.pragma.stockservice.infrastructure.output.jpa.mapper;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryEntityMapperTest {

    private CategoryEntityMapper categoryEntityMapper;

    @BeforeEach
    void setUp() {
        // Obtén una instancia del mapper generado por MapStruct
        categoryEntityMapper = Mappers.getMapper(CategoryEntityMapper.class);
    }

    @Test
    void testToEntity() {
        // Arrange
        Category category = new Category("Beverages", "Drinks and beverages");

        // Act
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);

        // Assert
        assertNotNull(categoryEntity);
        assertEquals(category.getName(), categoryEntity.getName());
        assertEquals(category.getDescription(), categoryEntity.getDescription());
    }

    @Test
    void testToCategory() {
        // Arrange
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Beverages", "Drinks and beverages");

        // Act
        Category category = categoryEntityMapper.toCategory(categoryEntity);

        // Assert
        assertNotNull(category);
        assertEquals(categoryEntity.getId(), category.getId());
        assertEquals(categoryEntity.getName(), category.getName());
        assertEquals(categoryEntity.getDescription(), category.getDescription());
    }
}