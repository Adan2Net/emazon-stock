package com.pragma.stockservice.infrastructure.output.jpa.mapper;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.stockservice.application.dto.CategoryPaginationResponse;
import com.pragma.stockservice.application.dto.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    @Test
    void testToCategoryPaginationResponse() {
        // Arrange
        Category category = new Category( "Beverages", "Drinks and beverages");
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
        assertEquals(expectedPaginationResponse.pageNumber(), paginationResponse.pageNumber());
        assertEquals(expectedPaginationResponse.pageSize(), paginationResponse.pageSize());
        assertEquals(expectedPaginationResponse.totalElements(), paginationResponse.totalElements());
        assertEquals(expectedPaginationResponse.totalPages(), paginationResponse.totalPages());
        assertEquals(expectedPaginationResponse.isFirst(), paginationResponse.isFirst());
        assertEquals(expectedPaginationResponse.isLast(), paginationResponse.isLast());

        assertNotNull(paginationResponse.content());
        assertEquals(expectedPaginationResponse.content().size(), paginationResponse.content().size());

        for (int i = 0; i < expectedPaginationResponse.content().size(); i++) {
            CategoryResponse expected = expectedPaginationResponse.content().get(i);
            CategoryResponse actual = paginationResponse.content().get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getDescription(), actual.getDescription());
        }
    }

//    @Test
//    void testToListPage() {
//        // Arrange
//        CategoryResponse categoryResponse = new CategoryResponse(1L, "Beverages", "Drinks and beverages");
//        Category category = new Category("Beverages", "Drinks and beverages");
//        List<CategoryResponse> responses = List.of(categoryResponse);
//        CategoryPaginationResponse<CategoryResponse> categoryPaginationResponse = new CategoryPaginationResponse<>(
//                responses, 0, 10, 1, 1, false, false
//        );
//
//        ListPage<Category> expectedListPage = new ListPage<>(
//                List.of(category),
//                0, 10, 1, 1, false, false
//        );
//
//        // Act
//        ListPage<Category> listPage = categoryEntityMapper.toListPage(categoryPaginationResponse);
//
//        // Assert
//        assertNotNull(listPage);
//        assertEquals(expectedListPage.getPageNumber(), listPage.getPageNumber());
//        assertEquals(expectedListPage.getPageSize(), listPage.getPageSize());
//        assertEquals(expectedListPage.getTotalElements(), listPage.getTotalElements());
//        assertEquals(expectedListPage.getTotalPages(), listPage.getTotalPages());
//        assertEquals(expectedListPage.isFirst(), listPage.isFirst());
//        assertEquals(expectedListPage.isLast(), listPage.isLast());
//
//        // Compare individual items
//        assertEquals(expectedListPage.getContent().size(), listPage.getContent().size());
//        for (int i = 0; i < expectedListPage.getContent().size(); i++) {
//            Category expected = expectedListPage.getContent().get(i);
//            Category actual = listPage.getContent().get(i);
//            assertEquals(expected.getName(), actual.getName());
//            assertEquals(expected.getDescription(), actual.getDescription());
//            // Ensure ID matching (if applicable)
//            // assertEquals(expected.getId(), actual.getId());
//        }
//    }
}