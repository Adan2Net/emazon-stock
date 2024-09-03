package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.api.ICategoryServicePort;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.domain.spi.ICategoryPersistencePort;
import com.pragma.stockservice.infrastructure.exception.InvalidSortDirectionException;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }


    @Override
    public Category save(Category category) {
        return categoryPersistencePort.save(category);
    }

    @Override
    public ListPage<Category> getPaginationCategoriesByAscAndDesc(SortDirection sortDirection, int page, int size) {
        // Implementación del método de paginación y ordenación
        ListPage<Category> listPageCategory = categoryPersistencePort.getPaginationCategories(page, size);

        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
            listPageCategory.setContent(
                    listPageCategory.getContent().stream()
                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
                                    Comparator.comparing(Category::getName) :
                                    Comparator.comparing(Category::getName).reversed())
                            .toList()
            );
        } else {
            throw new InvalidSortDirectionException();
        }

        return listPageCategory;
    }
}
