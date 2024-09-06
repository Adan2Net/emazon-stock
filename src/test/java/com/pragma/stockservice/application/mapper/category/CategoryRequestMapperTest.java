package com.pragma.stockservice.application.mapper.category;

import com.pragma.stockservice.application.dto.category.CategoryRequest;
import com.pragma.stockservice.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryRequestMapperTest {

    private final CategoryRequestMapper mapper = Mappers.getMapper(CategoryRequestMapper.class);

    @Test
    void testToCategory() {
        // Arrange
        String name = "Test Category";
        String description = "Test Description";
        CategoryRequest request = new CategoryRequest(name, description);

        // Act
        Category category = mapper.toCategory(request);

        // Assert
        assertEquals(request.getName(), category.getName(), "The category name should match the request name");
        assertEquals(request.getDescription(), category.getDescription(), "The category description should match the request description");
    }
}