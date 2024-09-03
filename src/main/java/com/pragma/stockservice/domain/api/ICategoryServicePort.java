package com.pragma.stockservice.domain.api;

import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;

public interface ICategoryServicePort {

    Category save(Category category);
    ListPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size);
}
