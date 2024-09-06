package com.pragma.stockservice.application.mapper.category;

import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.category.CategoryResponse;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);
    Category toCategory(CategoryResponse response);
    SortDirection toSortDirection(SortDirectionRequest sortDirectionRequest);
}
