package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrandSuccess() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");

        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> brandUseCase.save(brand));
        verify(brandPersistencePort, times(1)).save(brand);
    }

    @Test
    void testSaveBrand_BrandAlreadyExists() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");

        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.of(brand));

        // Act & Assert
        assertThrows(DuplicateBrandNameException.class, () -> brandUseCase.save(brand));

        verify(brandPersistencePort, times(0)).save(brand);
    }

    @Test
    void testSaveBrand_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Brand brand = new Brand("Toys", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(brandPersistencePort).save(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.save(brand);
        });

        assertEquals("Database connection error", exception.getMessage());
    }

    @Test
    void testGetPaginationBrandsByAscAndDesc_whenSortDirectionIsAsc_shouldReturnSortedBrandsAscending() {
        // Arrange
        Brand brand1 = new Brand("Books", "Various books");
        Brand brand2 = new Brand("Electronics", "All kinds of electronics");
        Brand brand3 = new Brand("Toys", "Various toys");

        List<Brand> brandList = Arrays.asList(brand1, brand2, brand3);
        ListPage<Brand> listPage = new ListPage<>(brandList, 0, 10, 3L, 1, true, true);

        when(brandPersistencePort.getPaginationBrand(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Brand> result = brandUseCase.getPaginationBrandByAscAndDesc(SortDirection.ASC, 0, 10);

        // Assert
        List<Brand> sortedBrands = result.getContent();
        assertEquals(3, sortedBrands.size());
        assertEquals("Books", sortedBrands.get(0).getName());
        assertEquals("Electronics", sortedBrands.get(1).getName());
        assertEquals("Toys", sortedBrands.get(2).getName());

        verify(brandPersistencePort, times(1)).getPaginationBrand(0, 10);
    }

    @Test
    void testGetPaginationBrandsByAscAndDesc_whenSortDirectionIsDesc_shouldReturnSortedBrandsDescending() {
        // Arrange
        Brand brand1 = new Brand("Books", "Various books");
        Brand brand2 = new Brand("Electronics", "All kinds of electronics");
        Brand brand3 = new Brand("Toys", "Various toys");

        List<Brand> brandList = Arrays.asList(brand1, brand2, brand3);
        ListPage<Brand> listPage = new ListPage<>(brandList, 0, 10, 3L, 1, true, true);

        when(brandPersistencePort.getPaginationBrand(0, 10)).thenReturn(listPage);

        // Act
        ListPage<Brand> result = brandUseCase.getPaginationBrandByAscAndDesc(SortDirection.DESC, 0, 10);

        // Assert
        List<Brand> sortedBrands = result.getContent();
        assertEquals(3, sortedBrands.size());
        assertEquals("Toys", sortedBrands.get(0).getName());
        assertEquals("Electronics", sortedBrands.get(1).getName());
        assertEquals("Books", sortedBrands.get(2).getName());

        verify(brandPersistencePort, times(1)).getPaginationBrand(0, 10);
    }
}