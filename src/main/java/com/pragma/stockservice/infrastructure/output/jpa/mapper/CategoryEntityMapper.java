package com.pragma.stockservice.infrastructure.output.jpa.mapper;


import com.pragma.stockservice.application.dto.category.CategoryPaginationResponse;
import com.pragma.stockservice.application.dto.category.CategoryResponse;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.infrastructure.output.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryEntityMapper {

    CategoryEntity toEntity(Category category);

    Category toCategory(CategoryEntity categoryEntity);

    CategoryPaginationResponse<CategoryResponse> toCategoryPaginationResponse(ListPage<Category> categoryEntity);
    ListPage<Category> toListPage(CategoryPaginationResponse categoryPaginationResponse);
}
