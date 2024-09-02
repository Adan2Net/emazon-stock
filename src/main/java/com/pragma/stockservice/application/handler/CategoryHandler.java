package com.pragma.stockservice.application.handler;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryPersistencePort persistencePort;

    @Override
    public Category save(Category category) {
        persistencePort.findByName(category.getName())
                .ifPresent(existingCategory -> {
                    throw new DuplicateCategoryNameException("Category with this name already exists");
                });

        return persistencePort.save(category);
    }
}
