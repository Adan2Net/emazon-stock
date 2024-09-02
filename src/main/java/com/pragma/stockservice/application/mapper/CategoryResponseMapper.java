package com.pragma.stockservice.application.mapper;

import com.pragma.stockservice.application.dto.CategoryResponse;
import com.pragma.stockservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {

    CategoryResponse toCategoryResponse(Category category);
    Category toCategory(CategoryResponse response);
}