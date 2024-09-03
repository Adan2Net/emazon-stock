package com.pragma.stockservice.domain.usercase;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.domain.usecase.CategoryUseCase;
import com.pragma.stockservice.infrastructure.exception.InvalidSortDirectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory() {
        // Arrange
        Category category = new Category("Test Category",  "Test Description");
        when(categoryPersistencePort.save(category)).thenReturn(category);

        // Act
        Category savedCategory = categoryUseCase.save(category);

        // Assert
        assertEquals(category, savedCategory, "The saved category should match the returned category");
    }

    @Test
    void testGetPaginationCategoriesByAscAndDescWithAscSortDirection() {
        // Arrange
        Category category1 = new Category("A Category",  "Description");
        Category category2 = new Category("B Category",  "Description");
        List<Category> categories = List.of(category2, category1);
        ListPage<Category> listPage = new ListPage<>(categories, 0, 10, 2, 1, true, true);
        when(categoryPersistencePort.getPaginationCategories(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC, 0, 10);

        // Assert
        assertEquals(2, result.getContent().size(), "The content size should match");
        assertEquals("A Category", result.getContent().get(0).getName(), "The first category should be 'A Category'");
        assertEquals("B Category", result.getContent().get(1).getName(), "The second category should be 'B Category'");
    }

    @Test
    void testGetPaginationCategoriesByAscAndDescWithDescSortDirection() {
        // Arrange
        Category category1 = new Category("A Category",  "Description");
        Category category2 = new Category("B Category",  "Description");
        List<Category> categories = List.of(category1, category2);
        ListPage<Category> listPage = new ListPage<>(categories, 0, 10, 2, 1, true, true);
        when(categoryPersistencePort.getPaginationCategories(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC, 0, 10);

        // Assert
        assertEquals(2, result.getContent().size(), "The content size should match");
        assertEquals("B Category", result.getContent().get(0).getName(), "The first category should be 'B Category'");
        assertEquals("A Category", result.getContent().get(1).getName(), "The second category should be 'A Category'");
    }
}
