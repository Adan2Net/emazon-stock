package com.pragma.stockservice.domain.api;

import com.pragma.stockservice.domain.model.Category;

public interface ICategoryServicePort {

    Category save(Category category);
}
