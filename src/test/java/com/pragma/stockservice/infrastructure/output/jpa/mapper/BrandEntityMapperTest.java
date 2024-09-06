package com.pragma.stockservice.infrastructure.output.jpa.mapper;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BrandEntityMapperTest {

    private BrandEntityMapper brandEntityMapper;

    @BeforeEach
    void setUp() {
        // Obtén una instancia del mapper generado por MapStruct
        brandEntityMapper = Mappers.getMapper(BrandEntityMapper.class);
    }

    @Test
    void testToEntity() {
        // Arrange
        Brand brand = new Brand("Nike", "Sports brand");

        // Act
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);

        // Assert
        assertNotNull(brandEntity);
        assertEquals(brand.getName(), brandEntity.getName());
        assertEquals(brand.getDescription(), brandEntity.getDescription());
    }

    @Test
    void testToBrand() {
        // Arrange
        BrandEntity brandEntity = new BrandEntity(1L, "Nike", "Sports brand");

        // Act
        Brand brand = brandEntityMapper.toBrand(brandEntity);

        // Assert
        assertNotNull(brand);
        assertEquals(brandEntity.getId(), brand.getId());
        assertEquals(brandEntity.getName(), brand.getName());
        assertEquals(brandEntity.getDescription(), brand.getDescription());
    }
}