package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import com.pragma.stockservice.infrastructure.exception.InvalidSortDirectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testCreateCategorySuccess() {
        // Arrange
        Category category = new Category("Books", "Various books");

        when(categoryPersistencePort.findByName(category.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> categoryUseCase.save(category));
        verify(categoryPersistencePort, times(1)).save(category);
    }

    @Test
    void testSaveCategory_CategoryAlreadyExists() {
        // Arrange
        Category category = new Category("Books", "Various books");

        when(categoryPersistencePort.findByName(category.getName())).thenReturn(Optional.of(category));

        // Act & Assert
        assertThrows(DuplicateCategoryNameException.class, () -> categoryUseCase.save(category));

        // Verifica que el método save no se llamó
        verify(categoryPersistencePort, times(0)).save(category);
    }

    @Test
    void testSaveCategory_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Category category = new Category("Toys", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(categoryPersistencePort).save(any(Category.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryUseCase.save(category);
        });

        assertEquals("Database connection error", exception.getMessage());
    }

    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsAsc_shouldReturnSortedCategoriesAscending() {
        // Arrange
        Category category1 = new Category("Books", "Various books");
        Category category2 = new Category("Electronics", "All kinds of electronics");
        Category category3 = new Category("Toys", "Various toys");

        List<Category> categoryList = Arrays.asList(category1, category2, category3);
        ListPage<Category> listPage = new ListPage<>(categoryList, 0, 10, 3L, 1, true, true);

        when(categoryPersistencePort.getPaginationCategories(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.ASC, 0, 10);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Books", sortedCategories.get(0).getName());
        assertEquals("Electronics", sortedCategories.get(1).getName());
        assertEquals("Toys", sortedCategories.get(2).getName());

        verify(categoryPersistencePort, times(1)).getPaginationCategories(0, 10);
    }

    @Test
    void testGetPaginationCategoriesByAscAndDesc_whenSortDirectionIsDesc_shouldReturnSortedCategoriesDescending() {
        // Arrange
        Category category1 = new Category("Books", "Various books");
        Category category2 = new Category("Electronics", "All kinds of electronics");
        Category category3 = new Category("Toys", "Various toys");

        List<Category> categoryList = Arrays.asList(category1, category2, category3);
        ListPage<Category> listPage = new ListPage<>(categoryList, 0, 10, 3L, 1, true, true);

        when(categoryPersistencePort.getPaginationCategories(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Category> result = categoryUseCase.getPaginationCategoriesByAscAndDesc(SortDirection.DESC, 0, 10);

        // Assert
        List<Category> sortedCategories = result.getContent();
        assertEquals(3, sortedCategories.size());
        assertEquals("Toys", sortedCategories.get(0).getName());
        assertEquals("Electronics", sortedCategories.get(1).getName());
        assertEquals("Books", sortedCategories.get(2).getName());

        verify(categoryPersistencePort, times(1)).getPaginationCategories(0, 10);
    }
}