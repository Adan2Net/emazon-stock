package com.pragma.stockservice.application.handler;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryHandlerTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveCategorySuccessfully() {
        // Arrange
        Category category = new Category("ValidName", "ValidDescription");
        when(categoryPersistencePort.findByName(anyString())).thenReturn(Optional.empty());
        when(categoryPersistencePort.save(any(Category.class))).thenReturn(category);

        // Act
        Category savedCategory = categoryHandler.save(category);

        // Assert
        verify(categoryPersistencePort, times(1)).findByName("ValidName");
        verify(categoryPersistencePort, times(1)).save(category);
        assertEquals(category, savedCategory);
    }

    @Test
    void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
        // Arrange
        Category category = new Category("DuplicateName", "ValidDescription");
        when(categoryPersistencePort.findByName(anyString())).thenReturn(Optional.of(category));

        // Act & Assert
        assertThrows(DuplicateCategoryNameException.class, () -> categoryHandler.save(category));

        verify(categoryPersistencePort, times(1)).findByName("DuplicateName");
        verify(categoryPersistencePort, never()).save(any(Category.class));
    }
}