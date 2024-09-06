package com.pragma.stockservice.application.mapper.brand;

import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandResponseMapperTest {

    private final BrandResponseMapper mapper = Mappers.getMapper(BrandResponseMapper.class);

    @Test
    void testToBrandResponse() {
        // Arrange
        Brand brand = new Brand("Test Brand", "Test Brand");

        // Act
        BrandResponse response = mapper.toBrandResponse(brand);

        // Assert
        assertEquals(brand.getId(), response.getId(), "The brand ID should match the response ID");
        assertEquals(brand.getName(), response.getName(), "The brand name should match the response name");
        assertEquals(brand.getDescription(), response.getDescription(), "The brand description should match the response description");
    }


    @Test
    void testToBrand() {
        // Arrange
        BrandResponse response = new BrandResponse(1L, "Test Brand", "Test Description");

        // Act
        Brand brand = mapper.toBrand(response);

        // Assert
        assertEquals(response.getId(), brand.getId(), "The brand ID should match the response ID");
        assertEquals(response.getName(), brand.getName(), "The brand name should match the response name");
        assertEquals(response.getDescription(), brand.getDescription(), "The brand description should match the response description");
    }
}
