package com.pragma.stockservice.application.mapper.brand;

import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandRequestMapper {

    Brand toBrand(BrandRequest request);
}
