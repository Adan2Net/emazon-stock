package com.pragma.stockservice.infrastructure.output.jpa.adapter;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCategory_Success() {
        // Arrange
        Category category = new Category("Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        Category savedCategory = categoryJpaAdapter.save(category);

        // Assert
        assertEquals(category.getName(), savedCategory.getName());
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    void testSaveCategory_CategoryAlreadyExists() {
        // Arrange
        Category category = new Category("Electronics", "Devices and gadgets");
        CategoryEntity existingCategoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(existingCategoryEntity));

        // Act & Assert
        assertThrows(DuplicateCategoryNameException.class, () -> categoryJpaAdapter.save(category));

        // Verifica que el método save no se llame
        verify(categoryRepository, times(0)).save(any(CategoryEntity.class));
    }

    @Test
    void testFindByName_Success() {
        // Arrange
        String categoryName = "Electronics";
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category(categoryName, "Devices and gadgets");
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        Optional<Category> foundCategory = categoryJpaAdapter.findByName(categoryName);

        // Assert
        assertTrue(foundCategory.isPresent());
        assertEquals(categoryName, foundCategory.get().getName());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        String categoryName = "NonExistentCategory";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // Act
        Optional<Category> foundCategory = categoryJpaAdapter.findByName(categoryName);

        // Assert
        assertFalse(foundCategory.isPresent());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    void testGetPaginationCategory_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        CategoryEntity categoryEntity = new CategoryEntity();
        Category category = new Category("Electronics", "Devices and gadgets");
        Page<CategoryEntity> categoryPage = new PageImpl<>(Collections.singletonList(categoryEntity), pageable, 1);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Act
        ListPage<Category> result = categoryJpaAdapter.getPaginationCategories(page, size);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(category.getName(), result.getContent().get(0).getName());
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetPaginationCategory_EmptyPage() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(categoryRepository.findAll(pageable)).thenReturn(emptyPage);

        // Act
        ListPage<Category> result = categoryJpaAdapter.getPaginationCategories(page, size);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        verify(categoryRepository, times(1)).findAll(pageable);
    }
}