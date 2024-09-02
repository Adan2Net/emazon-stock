package com.pragma.stockservice.application.mapper;


import com.pragma.stockservice.application.dto.CategoryRequest;
import com.pragma.stockservice.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    Category toCategory(CategoryRequest request);
}
