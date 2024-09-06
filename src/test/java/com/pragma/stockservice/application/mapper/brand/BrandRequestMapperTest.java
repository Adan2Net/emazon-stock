package com.pragma.stockservice.application.mapper.brand;

import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandRequestMapperTest {

    private final BrandRequestMapper mapper = Mappers.getMapper(BrandRequestMapper.class);

    @Test
    void testToBrand() {
        // Arrange
        String name = "Test Brand";
        String description = "Test Description";
        BrandRequest request = new BrandRequest(name, description);

        // Act
        Brand brand = mapper.toBrand(request);

        // Assert
        assertEquals(request.getName(), brand.getName(), "The brand name should match the request name");
        assertEquals(request.getDescription(), brand.getDescription(), "The brand description should match the request description");
    }
}
