package com.pragma.stockservice.infrastructure.output.jpa.mapper;

import com.pragma.stockservice.application.dto.brand.BrandPaginationResponse;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    Brand toBrand(BrandEntity brandEntity);
    BrandPaginationResponse<BrandResponse> toBrandPaginationResponse(ListPage<Brand> brandEntity);
    ListPage<Brand> toListPage(BrandPaginationResponse brandPaginationResponse);
}
