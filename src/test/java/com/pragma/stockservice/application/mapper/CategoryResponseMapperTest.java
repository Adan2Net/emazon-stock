package com.pragma.stockservice.application.mapper;

import com.pragma.stockservice.application.dto.CategoryResponse;
import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.SortDirection;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryResponseMapperTest {

    private final CategoryResponseMapper mapper = Mappers.getMapper(CategoryResponseMapper.class);

    @Test
    void testToCategoryResponse() {
        // Arrange
        Category category = new Category("Test Category", "Test Category");

        // Act
        CategoryResponse response = mapper.toCategoryResponse(category);

        // Assert
        assertEquals(category.getId(), response.getId(), "The category ID should match the response ID");
        assertEquals(category.getName(), response.getName(), "The category name should match the response name");
        assertEquals(category.getDescription(), response.getDescription(), "The category description should match the response description");
    }


    @Test
    void testToCategory() {
        // Arrange
        CategoryResponse response = new CategoryResponse(1L, "Test Category", "Test Description");

        // Act
        Category category = mapper.toCategory(response);

        // Assert
        assertEquals(response.getId(), category.getId(), "The category ID should match the response ID");
        assertEquals(response.getName(), category.getName(), "The category name should match the response name");
        assertEquals(response.getDescription(), category.getDescription(), "The category description should match the response description");
    }

    @Test
    void testToSortDirection() {
        // Arrange
        SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;

        // Act
        SortDirection sortDirection = mapper.toSortDirection(sortDirectionRequest);

        // Assert
        assertEquals(SortDirection.ASC, sortDirection, "The SortDirection should be ASC");
    }
}