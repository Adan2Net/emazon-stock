package com.pragma.stockservice.infrastructure.output.jpa.adapter;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    public CategoryJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_CategoryAlreadyExists() {
        // Preparar datos
        Category category = new Category("Electronics", "Various electronics");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Electronics");

        // Configurar mocks
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(categoryEntity));

        // Ejecutar y verificar
        assertThatExceptionOfType(DuplicateCategoryNameException.class)
                .isThrownBy(() -> categoryJpaAdapter.save(category))
                .withMessage("Category with this name already exists");
    }

    @Test
    void testSave_CategorySavedSuccessfully() {
        // Preparar datos
        Category category = new Category("Electronics", "Various electronics");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Electronics");

        // Configurar mocks
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Ejecutar y verificar
        Category savedCategory = categoryJpaAdapter.save(category);

        assertThat(savedCategory).isEqualTo(category);
    }

    @Test
    void testFindByName_CategoryFound() {
        // Preparar datos
        Category category = new Category("Electronics", "Various electronics");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Electronics");

        // Configurar mocks
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        // Ejecutar y verificar
        Optional<Category> foundCategory = categoryJpaAdapter.findByName("Electronics");

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get()).isEqualTo(category);
    }

    @Test
    void testFindByName_CategoryNotFound() {
        // Configurar mocks
        when(categoryRepository.findByName("NonExistingCategory")).thenReturn(Optional.empty());

        // Ejecutar y verificar
        Optional<Category> foundCategory = categoryJpaAdapter.findByName("NonExistingCategory");

        assertThat(foundCategory).isNotPresent();
    }
}