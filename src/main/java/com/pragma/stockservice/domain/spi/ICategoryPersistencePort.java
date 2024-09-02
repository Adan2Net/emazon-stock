package com.pragma.stockservice.domain.spi;

import com.pragma.stockservice.domain.model.Category;

import java.util.Optional;

public interface ICategoryPersistencePort {

    Category save(Category category);
    Optional<Category> findByName(String name);
}
