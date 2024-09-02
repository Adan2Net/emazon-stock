package com.pragma.stockservice.domain.usercase;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.domain.usecase.CategoryUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCategory() {
        // Arrange
        Category category = new Category("Test Category", "Description");
        Category savedCategory = new Category("Test Category", "Description");
        when(categoryPersistencePort.save(category)).thenReturn(savedCategory);

        // Act
        Category result = categoryUseCase.save(category);

        // Assert
        assertEquals(savedCategory, result, "The saved category should be equal to the result");
    }
}