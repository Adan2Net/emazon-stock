package com.pragma.stockservice.infrastructure.output.jpa.adapter;


import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    //validacion recomendable en dominio
    public Category save(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new DuplicateCategoryNameException("Category with this name already exists");
        }
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        CategoryEntity savedEntity = categoryRepository.save(categoryEntity);
        return categoryEntityMapper.toCategory(savedEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toCategory);
    }
}
