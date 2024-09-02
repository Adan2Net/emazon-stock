package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.api.ICategoryServicePort;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;

public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public Category save(Category category) {
        return categoryPersistencePort.save(category);
    }
}
