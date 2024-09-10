package com.pragma.stockservice.infrastructure.output.jpa.mapper;

import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.brand.BrandPaginationResponse;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

   @Test
    void testToBrandPaginationResponse() {
        // Arrange
        List<Brand> brandList = List.of(new Brand("Nike", "Sports brand"));
        ListPage<Brand> listPage = new ListPage<>(brandList, 0, 10, 1L, 1, true, true);

        // Act
        BrandPaginationResponse<BrandResponse> paginationResponse = brandEntityMapper.toBrandPaginationResponse(listPage);

        // Assert
        assertNotNull(paginationResponse);
        assertEquals(1, paginationResponse.content().size());
        assertEquals("Nike", paginationResponse.content().get(0).getName());
        assertEquals("Sports brand", paginationResponse.content().get(0).getDescription());
        assertEquals(0, paginationResponse.pageNumber());
        assertEquals(10, paginationResponse.pageSize());
        assertEquals(1L, paginationResponse.totalElements());
        assertEquals(1, paginationResponse.totalPages());
        assertEquals(false, paginationResponse.isFirst());
        assertEquals(false, paginationResponse.isLast());
    }

    // Arrange
    SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;
    SortDirection sortDirection = SortDirection.ASC;
    int page = 0;
    int size = 10;
}