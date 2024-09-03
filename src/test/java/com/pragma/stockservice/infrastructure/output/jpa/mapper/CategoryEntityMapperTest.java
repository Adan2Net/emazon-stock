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

   /* @Test
    void testToCategoryPaginationResponse() {
        // Arrange
        Category category = new Category("Beverages", "Drinks and beverages");
        List<Category> categories = List.of(category);
        ListPage<Category> listPage = new ListPage<>(categories, 0, 10, 1, 1, true, true);

        CategoryResponse categoryResponse = new CategoryResponse(1L, "Beverages", "Drinks and beverages");
        CategoryPaginationResponse<CategoryResponse> expectedPaginationResponse = new CategoryPaginationResponse<>(
                List.of(categoryResponse),
                0, 10, 1, 1, true, true
        );

        // Act
        CategoryPaginationResponse<CategoryResponse> paginationResponse = categoryEntityMapper.toCategoryPaginationResponse(listPage);

        // Assert
        assertNotNull(paginationResponse);
        assertEquals(expectedPaginationResponse.content(), paginationResponse.content());
        assertEquals(expectedPaginationResponse.pageNumber(), paginationResponse.pageNumber());
        assertEquals(expectedPaginationResponse.pageSize(), paginationResponse.pageSize());
        assertEquals(expectedPaginationResponse.totalElements(), paginationResponse.totalElements());
        assertEquals(expectedPaginationResponse.totalPages(), paginationResponse.totalPages());
        assertEquals(expectedPaginationResponse.isFirst(), paginationResponse.isFirst());
        assertEquals(expectedPaginationResponse.isLast(), paginationResponse.isLast());
    }

    @Test
    void testToListPage() {
        // Arrange
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Beverages", "Drinks and beverages");
        Category category = new Category("Beverages", "Drinks and beverages");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Beverages", "Drinks and beverages");
        List<CategoryResponse> responses = List.of(categoryResponse);
        CategoryPaginationResponse<CategoryResponse> categoryPaginationResponse = new CategoryPaginationResponse<>(
                responses, 0, 10, 1, 1, false, false
        );

        ListPage<Category> expectedListPage = new ListPage<>(
                List.of(category),
                0, 10, 1, 1, false, false
        );

        // Mockea el mapper
        ListPage<Category> listPage = categoryEntityMapper.toListPage(categoryPaginationResponse);

        // Assert
        assertNotNull(listPage);
        assertEquals(expectedListPage, listPage);
    }*/
}