package com.pragma.stockservice.application.mapper.brand;

import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandResponseMapper {

    BrandResponse toBrandResponse(Brand brand);
    Brand toBrand(BrandResponse response);
    SortDirection toSortDirection(SortDirectionRequest sortDirectionRequest);
}
